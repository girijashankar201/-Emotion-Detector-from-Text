import java.util.*;

public class EmotionAnalyzer {
    // Predefined emotion keywords mapped to emotion categories
    private static final Map<String, List<String>> emotionKeywords = new HashMap<>();
    private static final Map<String, String> emotionEmojis = new HashMap<>();
    
    static {
        // Initialize emotion keywords
        emotionKeywords.put("joy", Arrays.asList(
            "happy", "joy", "excited", "delighted", "pleased", "ecstatic", 
            "thrilled", "wonderful", "amazing", "fantastic", "great", "good",
            "love", "loved", "awesome", "brilliant", "perfect", "smile", "laugh"
        ));
        
        emotionKeywords.put("sadness", Arrays.asList(
            "sad", "unhappy", "depressed", "miserable", "sorrow", "grief",
            "heartbroken", "tearful", "down", "blue", "hopeless", "lonely",
            "disappointed", "regret", "cry", "pain", "hurt", "loss"
        ));
        
        emotionKeywords.put("anger", Arrays.asList(
            "angry", "mad", "furious", "rage", "outraged", "annoyed",
            "irritated", "frustrated", "hate", "hatred", "resent", "bitter",
            "hostile", "aggressive", "violent", "temper", "outburst"
        ));
        
        emotionKeywords.put("fear", Arrays.asList(
            "afraid", "scared", "fear", "terrified", "anxious", "worried",
            "nervous", "panic", "dread", "horror", "frightened", "uneasy",
            "threatened", "intimidated", "apprehensive"
        ));
        
        emotionKeywords.put("surprise", Arrays.asList(
            "surprised", "shocked", "amazed", "astonished", "stunned",
            "unexpected", "wow", "incredible", "unbelievable", "startled"
        ));
        
        // Initialize emotion emojis
        emotionEmojis.put("joy", "😊");
        emotionEmojis.put("sadness", "😢");
        emotionEmojis.put("anger", "😠");
        emotionEmojis.put("fear", "😨");
        emotionEmojis.put("surprise", "😲");
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("🎭 Emotion Analyzer - Type 'exit' to quit 🎭");
        System.out.println("=" .repeat(50));
        
        while (true) {
            System.out.print("\nEnter a sentence or phrase: ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Thank you for using Emotion Analyzer! 👋");
                break;
            }
            
            if (input.isEmpty()) {
                System.out.println("Please enter some text.");
                continue;
            }
            
            analyzeEmotion(input);
        }
        
        scanner.close();
    }
    
    private static void analyzeEmotion(String input) {
        // Clean and prepare the input
        String cleanedInput = cleanInput(input);
        String[] words = cleanedInput.split("\\s+");
        
        // Count emotion occurrences
        Map<String, Integer> emotionCounts = new HashMap<>();
        Map<String, List<String>> detectedKeywords = new HashMap<>();
        
        // Initialize counts and keyword lists
        for (String emotion : emotionKeywords.keySet()) {
            emotionCounts.put(emotion, 0);
            detectedKeywords.put(emotion, new ArrayList<>());
        }
        
        // Analyze each word
        for (String word : words) {
            for (Map.Entry<String, List<String>> entry : emotionKeywords.entrySet()) {
                String emotion = entry.getKey();
                List<String> keywords = entry.getValue();
                
                if (keywords.contains(word)) {
                    emotionCounts.put(emotion, emotionCounts.get(emotion) + 1);
                    detectedKeywords.get(emotion).add(word);
                }
            }
        }
        
        // Display analysis results
        displayAnalysis(input, emotionCounts, detectedKeywords);
        
        // Determine and display dominant emotion
        String dominantEmotion = determineDominantEmotion(emotionCounts);
        displayDominantEmotion(dominantEmotion, emotionCounts);
    }
    
    private static String cleanInput(String input) {
        // Convert to lowercase and remove punctuation
        return input.toLowerCase()
                   .replaceAll("[^a-zA-Z\\s]", "")
                   .replaceAll("\\s+", " ")
                   .trim();
    }
    
    private static void displayAnalysis(String originalInput, 
                                      Map<String, Integer> emotionCounts,
                                      Map<String, List<String>> detectedKeywords) {
        
        System.out.println("\n📊 Analysis Results:");
        System.out.println("-".repeat(40));
        
        boolean foundKeywords = false;
        
        for (Map.Entry<String, Integer> entry : emotionCounts.entrySet()) {
            String emotion = entry.getKey();
            int count = entry.getValue();
            List<String> keywords = detectedKeywords.get(emotion);
            
            if (count > 0) {
                foundKeywords = true;
                String emoji = emotionEmojis.get(emotion);
                System.out.printf("%s %s: %d occurrence(s)%n", 
                    emoji, capitalize(emotion), count);
                
                if (!keywords.isEmpty()) {
                    System.out.println("   Keywords: " + String.join(", ", keywords));
                }
            }
        }
        
        if (!foundKeywords) {
            System.out.println("❓ No emotion keywords detected.");
            System.out.println("   The text appears to be neutral.");
        }
    }
    
    private static String determineDominantEmotion(Map<String, Integer> emotionCounts) {
        String dominantEmotion = "neutral";
        int maxCount = 0;
        
        for (Map.Entry<String, Integer> entry : emotionCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                dominantEmotion = entry.getKey();
            }
        }
        
        // If no emotions detected or tied counts, return neutral
        if (maxCount == 0) {
            return "neutral";
        }
        
        return dominantEmotion;
    }
    
    private static void displayDominantEmotion(String dominantEmotion, 
                                              Map<String, Integer> emotionCounts) {
        
        System.out.println("\n🎯 Dominant Emotion:");
        System.out.println("-".repeat(40));
        
        if (dominantEmotion.equals("neutral")) {
            System.out.println("😐 Neutral - No strong emotion detected");
        } else {
            String emoji = emotionEmojis.get(dominantEmotion);
            int count = emotionCounts.get(dominantEmotion);
            System.out.printf("%s %s - Detected %d keyword(s)%n", 
                emoji, capitalize(dominantEmotion), count);
        }
    }
    
    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}