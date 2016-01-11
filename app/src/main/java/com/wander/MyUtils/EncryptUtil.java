package com.wander.MyUtils;


import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by wander on IDEA.
 * Date:15-4-14
 * Email:18955260352@163.com
 */
public class EncryptUtil {

    /**
     * MD5  消息摘要  对输入的内容  进行唯一标识的计算，有点像身份证号
     *
     * @param data
     * @return
     */
    public static byte[] md5(byte[] data) {
        byte[] ret = null;
        if (data != null) {
            try {
                MessageDigest digest = MessageDigest.getInstance("MD5");
                ret = digest.digest(data);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * sha1  消息摘要  对输入的内容  进行唯一标识的计算，有点像身份证号
     *
     * @param data
     * @return
     */
    public static byte[] sha1(byte[] data) {
        byte[] ret = null;
        if (data != null) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-1");
                ret = digest.digest(data);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * RSA 加密部分，支持privateKey加密， 同时也支持publicKey
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] rsaEncrypt(byte[] data, Key key) {
        byte[] ret = null;
        if (data != null && key != null) {
            if (key instanceof PublicKey || key instanceof PrivateKey) {
                try {
                    Cipher cipher = Cipher.getInstance("RSA");
                    cipher.init(Cipher.ENCRYPT_MODE, key);
                    ret = cipher.doFinal(data);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    /**
     * RSA 解密部分，支持privateKey加密， 同时也支持publicKey
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] rsaDecrypt(byte[] data, Key key) {
        byte[] ret = null;
        if (data != null && key != null) {
            if (key instanceof PublicKey || key instanceof PrivateKey) {
                try {
                    Cipher cipher = Cipher.getInstance("RSA");
                    cipher.init(Cipher.DECRYPT_MODE, key);
                    ret = cipher.doFinal(data);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }


    /////AES 算法//////

    /**
     * 采用AES加密 使用AES。getInstence("AES")
     *
     * @param data
     * @param pwd
     * @return
     */
    public static byte[] aesEncrypt(byte[] data, byte[] pwd) {

        byte[] ret = null;
        if (data != null && pwd != null) {
            //检查密码长度  进行补充
            int plen = pwd.length;
            if (plen < 16) {//128bit
                byte[] fully = new byte[16];
                System.arraycopy(pwd, 0, fully, 0, plen);
                pwd = fully;
            }


            try {
                //AESDE 加密。算法部分是支持多种形式的
                //如果系的是aes，那么对应的解密方法，使用的方法必须是AES
                //AES的
                Cipher cipher = Cipher.getInstance("AES");
                //AES 算法密码部分使用
                SecretKeySpec key = new SecretKeySpec(pwd, "AES");

                cipher.init(Cipher.ENCRYPT_MODE, key);
                //加密数据
                ret = cipher.doFinal(data);


            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 采用AES解密 使用AES。getInstence("AES")
     *
     * @param data
     * @param pwd
     * @return
     */
    public static byte[] aesDecrypt(byte[] data, byte[] pwd) {

        byte[] ret = null;
        if (data != null && pwd != null) {
            //检查密码长度  进行补充
            int plen = pwd.length;
            if (plen < 16) {//128bit
                byte[] fully = new byte[16];
                System.arraycopy(pwd, 0, fully, 0, plen);
                pwd = fully;
            }


            try {
                //AESDE 加密。算法部分是支持多种形式的
                //如果系的是aes，那么对应的解密方法，使用的方法必须是AES
                //AES的
                Cipher cipher = Cipher.getInstance("AES");
                //AES 算法密码部分使用
                SecretKeySpec key = new SecretKeySpec(pwd, "AES");

                cipher.init(Cipher.DECRYPT_MODE, key);
                //解密数据
                ret = cipher.doFinal(data);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 使用密码和Iv两个参数进行加密，可以理解为用两个密码进行加密
     * Iv可以看作另外一个密码，对于解密而言，密码和IV同样要一致
     *
     * @param data
     * @param pwd
     * @return
     */
    public static byte[] aesIvEncrypt(byte[] data, byte[] pwd, byte[] ivData) {
        byte[] ret = null;
        if (data != null && pwd != null && ivData != null) {
            //检查密码长度  进行补充
            int plen = pwd.length;
            if (plen < 16) {//128bit
                byte[] fully = new byte[16];
                System.arraycopy(pwd, 0, fully, 0, plen);
                pwd = fully;
            }
            int ilen = ivData.length;
            if (ilen < 16) {//128bit
                byte[] fully = new byte[16];
                System.arraycopy(ivData, 0, fully, 0, ilen);
                ivData = fully;
            }

            try {
                //支持的算法
                //AES/CBC/PKCS5Padding
                //AES/ECB/PKCS5Padding
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

                SecretKeySpec key = new SecretKeySpec(pwd, "AES");

                IvParameterSpec iv = new IvParameterSpec(ivData);

                cipher.init(Cipher.ENCRYPT_MODE, key, iv);
                ret = cipher.doFinal(data);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 使用密码和Iv两个参数进行解密，可以理解为用两个密码进行加密
     * Iv可以看作另外一个密码，对于解密而言，密码和IV同样要一致
     *
     * @param data
     * @param pwd
     * @return
     */
    public static byte[] aesIvDecrypt(byte[] data, byte[] pwd, byte[] ivData) {
        byte[] ret = null;
        if (data != null && pwd != null && ivData != null) {
            //检查密码长度  进行补充
            int plen = pwd.length;
            if (plen < 16) {//128bit
                byte[] fully = new byte[16];
                System.arraycopy(pwd, 0, fully, 0, plen);
                pwd = fully;
            }
            int ilen = ivData.length;
            if (ilen < 16) {//128bit
                byte[] fully = new byte[16];
                System.arraycopy(ivData, 0, fully, 0, ilen);
                ivData = fully;
            }

            try {
                //支持的算法
                //AES/CBC/PKCS5Padding
                //AES/ECB/PKCS5Padding
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

                SecretKeySpec key = new SecretKeySpec(pwd, "AES");

                IvParameterSpec iv = new IvParameterSpec(ivData);

                cipher.init(Cipher.DECRYPT_MODE, key, iv);

                ret = cipher.doFinal(data);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static String toHex(byte[] data) {
        String ret = null;
        StringBuilder sb = new StringBuilder();
        if (data != null) {
            int len = data.length;
            for (int i = 0; i < len; i++) {
                int iv = data[i];
                int ih = (iv >> 4) & 0x0F;
                int il = iv & 0x0F;
                char ch, cl;
                if (ih > 9) {
                    ch = (char) ('A' + (ih - 10));
                } else {
                    ch = (char) ('0' + ih);
                }
                if (il > 9) {
                    cl = (char) ('A' + (il - 10));
                } else {
                    cl = (char) ('0' + il);
                }
                sb.append(ch).append(cl);
                ret = sb.toString();
            }
        }
        return ret;
    }

    public static byte[] fromHex(String hex) {
        byte[] ret = null;

        if (hex != null) {
            if (hex.length() % 2 == 0) {
                ret = new byte[hex.length() / 2];
                int len = hex.length() - 1;
                for (int i = 0, j = 0; i < len; i += 2, j++) {
                    char ch = hex.charAt(i);
                    char cl = hex.charAt(i + 1);
                    int ih, il;
                    if (ch >= 'A') {
                        ih = ch - 'A' + 10;
                    } else {
                        ih = ch - '0';
                    }
                    if (cl >= 'A') {
                        il = cl - 'A' + 10;
                    } else {
                        il = cl - '0';
                    }
                    ret[j] = (byte) (((ih & 0x0F) << 4) | (il & 0x0F));

                }
            }
        }
        return ret;

    }

    /**
     * DES加密算法
     *
     * @param data 原始数据
     * @param pwd  密码需要8个字节
     * @return byte[] 加密后的数据
     */
    public static byte[] desEncryot(byte[] data, byte[] pwd) {
        byte[] ret = null;
        if (data != null && pwd != null) {
            //创建加密工具Cipher,所有的加密解密全都用这个类
            try {
                Cipher cipher = Cipher.getInstance("DES");
                //初始化  可以控制加密还是解密
                //加密模式需要密码，而且DES 8个字节的密码
                //第二个参数是KEY类型   是加密系统中的特定类
                //对于DES加密来说，Key采用SecretKeyFactory来生成

                //加密api中所有的工具  都需要通过getInstance来获取
                SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
                //这个方法，生成实际的Key对象
                //参数就是释迦的密码数据信息
                //对于DES来说，就是DESKeySpec
                SecretKey secretKey = secretKeyFactory.generateSecret(new DESKeySpec(pwd));
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                //所有的加密解密都需要使用byte[]来完成
                //对于DES而言，如果数据是一个字节数组的话
                //doFinal返回的加密后的数据
                ret = cipher.doFinal();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * DESede(3DES)加密算法
     *
     * @param data 原始数据
     * @param pwd  密码需要24个字节
     * @return byte[] 加密后的数据
     */
    public static byte[] desedeEncryot(byte[] data, byte[] pwd) {
        byte[] ret = null;
        if (data != null && pwd != null) {
            int plen = pwd.length;
            if (plen < 24) {
                byte[] fully = new byte[24];
                System.arraycopy(pwd, 0, fully, 0, plen);
                pwd = fully;
            }
            try {
                Cipher cipher = Cipher.getInstance("DESede");
                SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DESede");
                SecretKey key = secretKeyFactory.generateSecret(new DESedeKeySpec(pwd));
                cipher.init(Cipher.ENCRYPT_MODE, key);
                ret = cipher.doFinal(data);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }

        }
        return ret;
    }

    /**
     * DES解密算法
     *
     * @param enData
     * @param pwd
     * @return
     */
    public static byte[] desDecrypt(byte[] enData, byte[] pwd) {
        byte[] ret = null;
        if (enData != null && pwd != null && pwd.length == 8) {
            try {
                Cipher cipher = Cipher.getInstance("DES");
                //初始化  可以控制加密还是解密
                //加密模式需要密码，而且DES 8个字节的密码
                //第二个参数是KEY类型   是加密系统中的特定类
                //对于DES加密来说，Key采用SecretKeyFactory来生成

                //加密api中所有的工具  都需要通过getInstance来获取
                SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
                //这个方法，生成实际的Key对象
                //参数就是释迦的密码数据信息
                //对于DES来说，就是DESKeySpec
                SecretKey secretKey = secretKeyFactory.generateSecret(new DESKeySpec(pwd));
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                ret = cipher.doFinal(enData);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * DESede解密
     *
     * @param enData
     * @param pwd
     * @return
     */
    public static byte[] desedeDecrypt(byte[] enData, byte[] pwd) {
        byte[] ret = null;
        if (enData != null && pwd != null && pwd.length == 8) {
            try {
                Cipher cipher = Cipher.getInstance("DES");
                //初始化  可以控制加密还是解密
                //加密模式需要密码，而且DES 8个字节的密码
                //第二个参数是KEY类型   是加密系统中的特定类
                //对于DES加密来说，Key采用SecretKeyFactory来生成

                //加密api中所有的工具  都需要通过getInstance来获取
                SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
                //这个方法，生成实际的Key对象
                //参数就是释迦的密码数据信息
                //对于DES来说，就是DESKeySpec
                SecretKey secretKey = secretKeyFactory.generateSecret(new DESKeySpec(pwd));
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                ret = cipher.doFinal(enData);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
}
