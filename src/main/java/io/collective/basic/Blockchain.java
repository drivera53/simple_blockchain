package io.collective.basic;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Blockchain {

    // ArrayList to store the blocks
    private ArrayList<Block> block_chain = new ArrayList<Block>();

    public boolean isEmpty() {
        return (size() == 0);
    }

    public void add(Block block) {
        block_chain.add(block);
    }

    public int size() {
        return block_chain.size();
    }

    public boolean isValid() throws NoSuchAlgorithmException {

        // todo - check an empty chain
        // todo - check a chain of one
        // todo - check a chain of many
        String prev_block_hash = "";
    
        for (int i = 0;
            i < block_chain.size();
            i++) {

            String curr_block_hash = block_chain.get(i).getHash();
            String curr_block_calculated_hash = block_chain.get(i).calculatedHash();
    
            // Check if block is mined
            if (!isMined(block_chain.get(i))) {
                return false;
            }

            // Check if curr_block hash is equal to calculatedHash
            if (!curr_block_hash.equals(curr_block_calculated_hash)) {
                return false;
            }

            // Check if prev_block hash is equal to curr_block previousHash
            if (!prev_block_hash.isEmpty()) {
                String curr_block_prev_hash = block_chain.get(i).getPreviousHash();
                if (!prev_block_hash.equals(curr_block_prev_hash)) {
                    return false;
                }
            }

            prev_block_hash = curr_block_hash;
        }
    
        // If all the hashes are equal
        // to the calculated hashes 
        // and block is mined
        // then the blockchain is valid
        return true;
    }

    /// Supporting functions that you'll need.

    public static Block mine(Block block) throws NoSuchAlgorithmException {
        Block mined = new Block(block.getPreviousHash(), block.getTimestamp(), block.getNonce());

        while (!isMined(mined)) {
            mined = new Block(mined.getPreviousHash(), mined.getTimestamp(), mined.getNonce() + 1);
        }
        return mined;
    }

    public static boolean isMined(Block minedBlock) {
        return minedBlock.getHash().startsWith("00");
        // return !minedBlock.getHash().isEmpty();
    }
}