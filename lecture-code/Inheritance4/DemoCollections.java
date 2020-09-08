import java.util.*;


public class DemoCollections {
    /**
     * Returns a lower case version of the string with
     * all characters except letters removed */
    public static String cleanString(String s) {
        return s.toLowerCase().replaceAll("[^a-z]", "");
    }

    /** Gets a list of all words in the file. */
    public static List<String> getWords(String inputFileName) {
        List<String> words = new ArrayList<String>();
        In in = new In(inputFileName);
        while (!in.isEmpty()) {
            String nextWords = cleanString(in.readString());
            words.add(nextWords);
        }
        return words;
    }

    /** Returns the count of number of unique words. */
    public static int countUniqueWords(List<String> words) {
        Set<String> wordSet = new HashSet<>();
        for (int i = 0; i < words.size(); i++) {
            String ithWord = words.get(i);
            wordSet.add(ithWord);
        }
        return wordSet.size();
    }

    /** Returns the number of times each target word appears in the word list. */
    public static Map<String, Integer> collectWordCount(List<String> words, List<String> targets) {
        Map<String, Integer> counts = new HashMap<String, Integer>();
        for (String t : targets) {
            /* Make a note that we have seen none of the words */
            counts.put(t, 0);
        }
        for (String w : words) {
            if (counts.containsKey(w)) {
                int oldCount = counts.get(w);
                counts.put(w, oldCount + 1);
            }
        }
        return counts;
    }

    public static void main(String[] args) {
        List<String> w = getWords("libraryOfBabylon.txt");
        System.out.println(w);
        System.out.println(countUniqueWords(w));

        List<String> targets = new ArrayList<>();
        targets.add("the");
        targets.add("babylon");
        targets.add("i");
        System.out.println(collectWordCount(w, targets));
    }
}