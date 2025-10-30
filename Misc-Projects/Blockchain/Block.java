import java.util.Date;
/**
 * Block class creates a "block" that holds data (message) and a hash (identifier of previous block's hash)
 * The hash of a given structure is akin to its DNA. It is a one of one fixed-length string representation of data!
 *
 */
public class Block {
    public String hash;
    public String previousHash;
    private String data; // this data represents a message
    private long timestamp; // in (ms)
    private int nonce;


        public Block(String previousHash, String data) { //Constructor for "Block" object
            this.data = data;
            this.previousHash = previousHash;
            this.timestamp = new Date().getTime();
            this.hash = calculateHash(); // This is only set after creating calculateHash, hash is now unique.
        }

        public String calculateHash() { //Use StringUtil to create a new unique hash from important parameters
            String calculatedHash = StringUtil.applySHA256(data + previousHash + Long.toString(timestamp) + Integer.toString(nonce));
            return calculatedHash;
        }

        public void mineBlock(int difficulty) {
            String target = new String(new char[difficulty]).replace('\0', '0'); // String of difficulty * "0"
            while(!hash.substring(0, difficulty).equals(target)) {
                nonce ++;
                hash = calculateHash();
            }
            System.out.println("Block Mined :" + hash);
        }
}
