package com.unnet.triangle.lvs.master.service.crypto;

public interface CryptoService {
	 /**
     * 
     * @param data
     * @return on success "string", on failure null
     */
    public String encrypt(String data);

    /**
     * 
     * @param data
     * @return on success "string", on failure null
     */
    public String decrypt(String data);
}
