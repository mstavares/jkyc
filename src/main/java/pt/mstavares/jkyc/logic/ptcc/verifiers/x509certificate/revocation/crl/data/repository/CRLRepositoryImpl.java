package pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.data.repository;

import com.google.inject.Inject;
import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.data.exceptions.CRLInvalidCacheException;
import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.data.storage.local.CRLLocalStorage;
import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.data.storage.remote.CRLRemoteStorage;
import pt.mstavares.jkyc.utilities.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.util.Date;

/**
 * This class is used to manage CRLs
 * @author Miguel Tavares
 */
public class CRLRepositoryImpl implements CRLRepository {

    /** This attribute is used to store and fetch CRLs locally */
    @Inject
    private CRLLocalStorage localStorage;

    /** This attribute is used to fetch CRLs remotely */
    @Inject
    private CRLRemoteStorage remoteStorage;


    /**
     * This method is used to store a certain CRL locally
     * @param crlURL CRL file name
     * @param inputStream contains the data to be written
     * @throws IOException
     */
    @Override
    public void store(String crlURL, InputStream inputStream) throws IOException {
        Logger.logInfo(CRLRepositoryImpl.class.getSimpleName(), "Storing " + crlURL);
        localStorage.store(crlURL, inputStream);
    }

    /**
     * This method is used to fetch a certain CRL by file name
     * @param crlURL CRL file name
     * @return InputStream with CRL content
     * @throws IOException
     * @throws CertificateException
     * @throws CRLException
     */
    @Override
    public InputStream fetch(String crlURL) throws IOException, CertificateException, CRLException {
        try {
            Logger.logInfo(CRLRepositoryImpl.class.getSimpleName(), "Fetching " + crlURL);
            InputStream is = localStorage.fetch(crlURL);
            validateCache(is);
            return is;
        } catch (FileNotFoundException ex) {
            Logger.logInfo(CRLRepositoryImpl.class.getSimpleName(), "CRL cache is not valid");
            return fetchCRL(crlURL);
        } catch (CRLInvalidCacheException e) {
            Logger.logInfo(CRLRepositoryImpl.class.getSimpleName(), "CRL cache is not valid");
            localStorage.delete(crlURL);
            return fetchCRL(crlURL);
        }
    }

    /**
     * This method is used to verify if a certain cached CRL is still valid
     * @param inputStream if yes, returns the InputStream, if not throws CRLInvalidCacheException
     * @throws CertificateException
     * @throws CRLException
     * @throws CRLInvalidCacheException
     * @throws IOException
     */
    private void validateCache(InputStream inputStream) throws CertificateException, CRLException, CRLInvalidCacheException, IOException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        X509CRL crl = (X509CRL) certificateFactory.generateCRL(inputStream);
        if(crl.getNextUpdate().before(new Date()))
            throw new CRLInvalidCacheException();
        inputStream.reset();
        Logger.logInfo(CRLRepositoryImpl.class.getSimpleName(), "CRL cache valid");
    }

    /**
     * This method fetch a certain CRLs, store it locally and then return an InputStream with the CRL content
     * @param crlUrl CRL URL to being fetched
     * @return InputStream with CRL content
     * @throws IOException
     * @throws CertificateException
     * @throws CRLException
     */
    private InputStream fetchCRL(String crlUrl) throws IOException, CertificateException, CRLException {
        InputStream is = remoteStorage.fetch(crlUrl);
        localStorage.store(crlUrl, is);
        return fetch(crlUrl);
    }
}
