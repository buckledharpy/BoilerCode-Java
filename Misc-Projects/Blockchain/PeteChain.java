import java.util.ArrayList;
import com.google.gson.GsonBuilder;

public class PeteChain {

    public static ArrayList<Block> blockchain = new ArrayList<>();
    public static int difficulty = 5;

    public static void main(String[] args) {

        blockchain.add(new Block("This is the first block", "0"));
        System.out.println("Mining block 1...");
        blockchain.get(0).mineBlock(difficulty);
        blockchain.add(new Block("This is the second block", "1"));
        System.out.println("Mining block 2...");
        blockchain.get(1).mineBlock(difficulty);
        blockchain.add(new Block("This is the third block", "2"));
        System.out.println("Mining block 3...");
        blockchain.get(2).mineBlock(difficulty);

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("\n The Blockchain: ");
        System.out.print(blockchainJson);
    }

    public static Boolean isValidChain() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for(int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            if (!currentBlock.hash.equals(previousBlock.hash)) { // This checks the current calculated and registered hash
                return false;
            }
            if (!previousBlock.hash.equals(currentBlock.hash)) { // This checks the previous hash and the registered previous hash
                return false;
            }
            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block has yet to be mined");
                return false;
            }
        }
        return true;
    }
}
