package net.elmsclose.nativeandroid;

import android.util.Base64;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * Created by derek on 29/04/2018.
 */

public class Utils
{
    /**
     * Load the PEM cert into a trust store
     * @param pemCertString
     * @return
     * @throws Exception
     */
    public KeyStore loadPEMTrustStore(String pemCertString) throws Exception
    {
        byte[] der = loadPemCertificate(new ByteArrayInputStream(pemCertString.getBytes()));
        ByteArrayInputStream derInputStream = new ByteArrayInputStream(der);
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) certificateFactory.generateCertificate(derInputStream);
        String alias = cert.getSubjectX500Principal().getName();

        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        trustStore.setCertificateEntry(alias, cert);

        return trustStore;
    }

    /**
     * A quick converter from a pemInputStream.
     *
     * @param pemInputStream The inputstream with PEM certificate
     * @return The character array of containing converted DER file
     * @throws IOException Issues with the PEM file reading
     */
    public static byte[] loadPemCertificate(InputStream pemInputStream) throws IOException
    {
        byte[] der = null;
        BufferedReader pemBufferedReader = new BufferedReader(new InputStreamReader(pemInputStream));
        try {
            StringBuilder pemStringBuilder = new StringBuilder();
            String line = pemBufferedReader.readLine();
            while ( line != null )
            {
                if (!line.startsWith("--"))
                {
                    pemStringBuilder.append(line);
                }
                line = pemBufferedReader.readLine();
            }
            String pem = pemStringBuilder.toString();
            der = Base64.decode(pem, Base64.DEFAULT);
        } finally {
            if (pemBufferedReader != null)
            {
                pemBufferedReader.close();
            }
        }
        return der;
    }

}
