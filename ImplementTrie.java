// 208.
class Trie {

    TrieNode root; //tracks root of trie

    public Trie() {
        root = new TrieNode(); //initially trie is empty
    }
    
    //time - O(length of word)
    public void insert(String word) {
        TrieNode current = root; //copy of root
        for(int i = 0; i < word.length(); i++)
        {
            //add current char into trie
            if(!current.containsKey(word.charAt(i)))
            {
                current.insertKey(word.charAt(i));
            }

            //current node links map has ref to current char at i
            current = current.getKey(word.charAt(i));
        }

        //all chars in word are inserted
        current.setWordEnd(); //mark word end
        return;
    }
    
    //time - O(length of word)
    public boolean search(String word) {
        TrieNode current = root; //copy of root
        for(int i = 0; i < word.length(); i++)
        {
            //check if current trie node links map has current char ref
            if(!current.containsKey(word.charAt(i)))
            {
                return false; //word absent as current char was not found
            }

            //current char present in links map of current trie node
            current = current.getKey(word.charAt(i));
        }

        return current.checkWordEnd(); //check if last trie node has word end set
    }
    
    //time - O(length of word)
    public boolean startsWith(String prefix) {
        TrieNode current = root; //copy of root
        for(int i = 0; i < prefix.length(); i++)
        {
            //check if current char is present in links map of current trie node
            if(!current.containsKey(prefix.charAt(i)))
            {
                return false;
            }

            //current char present in links map of current trie node
            current = current.getKey(prefix.charAt(i));
        }

        return true; //return true as all letters in given word are present in trie as a prefix
    }
}


class TrieNode
{
    private HashMap<Character, TrieNode> links; //tracks each character and maps to a reference trie node
    private boolean isWordEnd; //tracks whether the current node marks a word end

    public TrieNode()
    {
        this.links = new HashMap<>();
        this.isWordEnd = false;
    }

    //checks if the current trie node contains the given character in links map
    //time - O(1)
    public boolean containsKey(char key)
    {
        return this.links.containsKey(key);
    }

    //adds the given character in current node links
    //time - O(1)
    public void insertKey(char key)
    {
        this.links.put(key, new TrieNode());
        return;
    }

    //returns the trie node reference of given key
    //should be called only if containsKey() returns true
    //time - O(1)
    public TrieNode getKey(char key)
    {
        return this.links.get(key);
    }

    //checks if current trie node marks a word end
    //time - O(1)
    public boolean checkWordEnd()
    {
        return this.isWordEnd;
    }

    //marks current trie node as word end
    //time - O(1)
    public void setWordEnd()
    {
        this.isWordEnd = true;
        return;
    }
}

// 1804.
class Trie {

    TrieNode root;

    public Trie() {
        root = new TrieNode(); //initially trie is empty
    }
    
    //time - O(length of word)
    public void insert(String word) {
        TrieNode current = root; //copy of root
        for(int i = 0; i < word.length(); i++)
        {
            //if current node links doesn't have current char add it
            if(!current.containsKey(word.charAt(i)))
            {
                current.insertKey(word.charAt(i));
            }
            current = current.getKey(word.charAt(i)); //go to reference of current letter
            current.setPrefix(); //as one word starting with word[0, i] is there in key
        }

        current.setWordEnd(); //current word is inserted
        return;
    }
    
    //time - O(length of word)
    public int countWordsEqualTo(String word) {
        TrieNode current = root; //copy of root
        for(int i = 0; i < word.length(); i++)
        {
            //check if current letter is present in links
            if(!current.containsKey(word.charAt(i)))
            {
                return 0; //word absent as current letter is missing
            }
            current = current.getKey(word.charAt(i)); //go to ref of current letter
        }
        return current.getWordEnd();
    }
    
    //time - O(length of prefix)
    public int countWordsStartingWith(String prefix) {
        TrieNode current = root; //copy of root
        for(int i = 0; i < prefix.length(); i++)
        {
            //check if current letter is in links
            if(!current.containsKey(prefix.charAt(i)))
            {
                return 0;
            }
            current = current.getKey(prefix.charAt(i)); //got to ref of current letter
        }
        return current.getPrefix();
    }
    
    //time - O(length of word)
    public void erase(String word) {
        TrieNode current = root; //copy of root
        for(int i = 0; i < word.length(); i++)
        {
            //check if current letter is in links
            if(!current.containsKey(word.charAt(i)))
            {
                return;
            }
            current = current.getKey(word.charAt(i)); //go to ref of current letter
            current.reducePrefix(); //reduce prefix of all intermediate trie nodes 
        }
        //reduce word count of last trie nodes
        current.reduceWordEnd();
        return;
    }
}

class TrieNode
{
    private HashMap<Character, TrieNode> links; //maps each character to a trie node reference
    private int wordEndCount; //tracks the count of words ending at current trie node
    private int prefixCount; //tracks the count of prefixes ending at current trie node

    public TrieNode()
    {
        this.links = new HashMap<>();
        this.wordEndCount = 0;
        this.prefixCount = 0;
    }

    //checks if given key is present in links map
    public boolean containsKey(char key)
    {
        return this.links.containsKey(key);
    }

    //adds given key to links map and create a refrence trie node for it
    public void insertKey(char key)
    {
        this.links.put(key, new TrieNode());
        return;
    }

    //returns the trie node reference of given key from links map
    public TrieNode getKey(char key)
    {
        return this.links.get(key);
    }

    //increses the count of words for which current node is the end node by 1
    public void setWordEnd()
    {
        this.wordEndCount++;
        return;
    }

    //returns the count of words for which current node is last
    public int getWordEnd()
    {
        return this.wordEndCount;
    }

    //reduced the count of words for which the current node is last by 1
    public void reduceWordEnd()
    {
        this.wordEndCount--;
        return;
    }

    //increses the count of prefixes for which current node is the end node by 1
    public void setPrefix()
    {
        this.prefixCount++;
        return;
    }

    //returns the count of prefixes for which current node is last
    public int getPrefix()
    {
        return this.prefixCount;
    }

    //reduced the count of prefixes for which the current node is last by 1
    public void reducePrefix()
    {
        this.prefixCount--;
        return;
    }
}
