// 1858.
class Solution {
    public String longestWord(String[] words) {
        Trie trie = new Trie(); //create a trie object

        //insert all words into trie
        for(String word : words)
        {
            trie.insert(word);
        }

        String result = "";
        for(String word : words)
        {
            if(trie.checkIfAllPrefixExists(word))
            {
                //current word has all prefixes
                if(result.length() < word.length())
                {
                    result = word;
                }
                else if(result.length() == word.length() && word.compareTo(result) < 0)
                {
                    result = word;
                }
            }
        }

        return result;
    }
}

class Trie
{
    TrieNode root;

    public Trie()
    {
        root = new TrieNode();
    }

    //inserts given word into trie
    //time - O(length of word)
    public void insert(String word)
    {
        TrieNode current = root; //copy of root
        for(int i = 0; i < word.length(); i++)
        {
            //check if current has current letter in word
            if(!current.links.containsKey(word.charAt(i)))
            {
                current.links.put(word.charAt(i), new TrieNode()); //create a ref trie node for current letter and go to it
            }
            current = current.links.get(word.charAt(i));
        }

        current.isWordEnd = true; //current word fully inserted
        
        return;
    }

    //checks if all prefixes of given word are in trie
    //time - O(length of word)
    public boolean checkIfAllPrefixExists(String word)
    {
        TrieNode current = root; //copy of root
        for(int i = 0; i < word.length(); i++)
        {
            //get trie node for current letter
            if(!current.links.containsKey(word.charAt(i)))
            {
                return false; //current letter absent
            }
            current = current.links.get(word.charAt(i));

            //check if current prefix present
            if(!current.isWordEnd)
            {
                return false;
            }
        }

        return current.isWordEnd;
    }
}

class TrieNode
{
    public HashMap<Character, TrieNode> links; //tracks reference trie node for each char
    public boolean isWordEnd; //is true if current trie node is the end of a word

    public TrieNode()
    {
        this.links = new HashMap<>();
        this.isWordEnd = false;
    }
}
