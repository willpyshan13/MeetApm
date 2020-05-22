package com.meiyou.common.apm.okhttp.internal;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class XSSLSocketFactory extends SSLSocketFactory {
    //OKHTTP 用了反射，必须用 delegate
    //https://github.com/square/okhttp/issues/2323
    private SSLSocketFactory delegate;

    public XSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
        super();
        this.delegate = sslSocketFactory;
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return delegate.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return delegate.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket() throws IOException {
        return delegate.createSocket();
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return delegate.createSocket(host, port);
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort)
            throws IOException, UnknownHostException {
        return delegate.createSocket(host, port, localHost, localPort);
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return delegate.createSocket(host, port);
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int
            localPort) throws IOException {
        return delegate.createSocket(address, port, localAddress, localPort);
    }

    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws
            IOException {
        if (s instanceof XSocket) {
            Socket rawSocket = ((XSocket) s).getImpl();
            return new XSSLSocket((SSLSocket) delegate.createSocket(rawSocket, host, port, autoClose));
        } else {
            return delegate.createSocket(s, host, port, autoClose);
        }
    }
}
