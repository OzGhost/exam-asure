package ruf.exam.asure.service;

import ruf.exam.asure.entity.SystemConfiguration;
import ruf.exam.asure.repo.SystemConfigurationRepo;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.inject.Inject;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EncryptionService {

    @Inject SystemConfigurationRepo syscfgRepo;

    public String encrypt(String inp) {
        String salt = syscfgRepo.getValueBy(SystemConfiguration.SALT_KEY);
        if (salt == null) throw new RuntimeException("System initialization required!");
        return encrypt(inp, salt);
    }

    public String encrypt(String inp, String salt) {
        PBEKeySpec spec = new PBEKeySpec(inp.toCharArray(), salt.getBytes(), 10000, 256);
        byte[] hash = null;
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return Base64.getEncoder().encodeToString(hash);
    }
}

