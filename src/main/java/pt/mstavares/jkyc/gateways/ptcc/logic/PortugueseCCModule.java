package pt.mstavares.jkyc.gateways.ptcc.logic;

import com.google.inject.AbstractModule;

/**
 * This class is used by Guice to bind the interfaces with its implementations
 * @author Miguel Tavares
 */
public class PortugueseCCModule extends AbstractModule {

    /**
     * This method binds the interfaces with its implementations
     */
    @Override
    protected void configure() {
        bind(PortugueseCC.class).to(PortugueseCCImpl.class);
    }

}
