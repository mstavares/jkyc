package pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.data.storage.local;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class is used to fetch and store CRLs locally (caching)
 * @author Miguel Tavares
 */
public class CRLLocalStorageImpl implements CRLLocalStorage {

    /** This variable is used to give a name to the folder where CRLs will be stored */
    private static final String CRLS_DIR = "crls";

    /**
     * This method stores locally a certain CRL
     * @param crlUrl CRL URL used to file name
     * @param inputStream content that will be written in the file
     * @throws IOException
     */
    @Override
    public void store(final String crlUrl, final InputStream inputStream) throws IOException {
        File file = new File(buildPath(crlUrl));
        FileUtils.copyInputStreamToFile(inputStream, file);
    }

    /**
     * This method deletes a certain CRL stored locally
     * @param crlURL crlURL to be deleted
     */
    @Override
    public void delete(String crlURL) {
        File file = new File(buildPath(crlURL));
        file.delete();
    }

    /**
     * This method fetches a certain CRL by URL
     * @param crlUrl CRL URL to be fetched
     * @return ByteArrayInputStream containing the CRL
     * @throws IOException
     */
    @Override
    public ByteArrayInputStream fetch(final String crlUrl) throws IOException {
        File file = new File(buildPath(crlUrl));
        return new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
    }

    /**
     * This method build the local path to a certain CRL
     * @param crlUrl CRL URL
     * @return CRL local path
     */
    private String buildPath(final String crlUrl) {
        String[] crlUrlParts = crlUrl.split("/");
        return CRLS_DIR + File.separator + crlUrlParts[crlUrlParts.length - 1];
    }
}
