/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.common.util;

/**
 * 
 * 
 * @date 2023-11-21
 * @author Nathan Liao
 */
public class CryptoUtilsTest {
    
    public static void main(String[] args) {
        String salt = CryptoUtils.generateKey();
        String user = "admin";
        String password = "abc123";
        
        String pHex = CryptoUtils.sha256(password);
        System.out.println("PHex ==> " + pHex);
        String encrypt = CryptoUtils.encrypt(pHex.concat(user), salt);
        System.out.println("sale ==> " + salt + ", encrypt ==> " + encrypt);
        
        String decrypt = CryptoUtils.decrypt(encrypt, salt);
        System.out.println("decrypt ==> " + decrypt);
    }
    
}
