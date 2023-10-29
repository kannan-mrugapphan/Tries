// 588.
class FileSystem {

    TrieNode root; //tracks the root folder

    public FileSystem() {
        root = new TrieNode();
    }
    
    //returns lists of files and folders present in folder path in sorted order
    //returns file name in a list present in filde path
    //time - O(number of folders in path)
    public List<String> ls(String path) {
        String[] folderNames = path.split("/"); //split on / to get folder names
        TrieNode current = root; //copy of root
        List<String> result = new ArrayList<>(); //return list
        
        for(String folderName : folderNames)
        {
            if(folderName.length() == 0)
            {
                continue;
            }

            //check if current folder is present in current trie node
            if(!current.links.containsKey(folderName))
            {
                return new ArrayList<>(); //invalid path as intermediate folder is absent
            }
            current = current.links.get(folderName); //cd inside folder
        }

        //last folder reached
        //check if file
        if(current.isFile)
        {
            result.add(folderNames[folderNames.length - 1]); //add last name into result list and return
            return result;
        }

        //current node is a folder
        TreeSet<String> files = new TreeSet<>();
        for(String file : current.links.keySet())
        {
            files.add(file);
        }

        result = new ArrayList<>(files);
        return result;
    }
    
    //time - O(number of folders)
    public void mkdir(String path) {
        String[] folderNames = path.split("/");
        //because of preceeding /, split will have an empty string in 0th index
        TrieNode current = root; //copy of root
        for(String folder : folderNames)
        {
            if(folder.length() == 0)
            {
                continue;
            }
            
            if(!current.links.containsKey(folder))
            {
                current.links.put(folder, new TrieNode()); //if folder absent, create it
            }
            current = current.links.get(folder);
        }
        return;
    }
    
    //time - O(number of folders)
    public void addContentToFile(String path, String content) {
        String[] folderNames = path.split("/");
        TrieNode current = root; //copy of root
        for(String folder : folderNames)
        {
            if(folder.length() == 0)
            {
                continue;
            }

            if(!current.links.containsKey(folder))
            {
                current.links.put(folder, new TrieNode()); //if folder absent, create it
            }
            current = current.links.get(folder);
        }
        current.isFile = true;
        current.content += content;
        return;
    }
    
    //time - O(number of folders)
    public String readContentFromFile(String path) {
        String[] folderNames = path.split("/");
        TrieNode current = root; //copy of root
        for(String folder : folderNames)
        {
            if(folder.length() == 0)
            {
                continue;
            }

            if(!current.links.containsKey(folder))
            {
                current.links.put(folder, new TrieNode()); //if folder absent, create it
            }
            current = current.links.get(folder);
        }
        return current.content;
    }
}

class TrieNode
{
    HashMap<String, TrieNode> links; //each folder in current node is another trie node
    String content; //stores content if current node is a file
    boolean isFile; //tracks if this node is a file or not

    public TrieNode()
    {
        links = new HashMap<>();
        content = "";
        isFile = false;
    }
}
