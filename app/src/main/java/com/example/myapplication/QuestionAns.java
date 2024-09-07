package com.example.myapplication;

import java.util.Random;

public class QuestionAns {

    public static String[] question = {
            "What is the recommended daily water intake for an average adult?",
            "Which of the following is a benefit of regular exercise?",
            "What is a balanced diet?",
            "Which vitamin is primarily obtained from sunlight?",
            "What is the minimum number of steps recommended daily for maintaining good health?",
            "Which of these foods is high in antioxidants?",
            "How many hours of sleep is recommended for adults to maintain optimal health?",
            "Which type of fat is considered the healthiest?",
            "Which of these activities is best for improving flexibility?",
            "What is the role of fiber in the diet?"
    };

    public static String[][] choices = {
            {"A) 1-2 liters", "B) 2-3 liters", "C) 3-4 liters", "D) 4-5 liters"},
            {"A) Improved cardiovascular health", "B) Increased stress levels", "C) Decreased bone density", "D) Reduced energy levels"},
            {"A) A diet high in carbs and fats", "B) A diet that includes all food groups in proper proportions",
                    "C) A diet consisting only of protein", "D) A diet that excludes all sugars"},
            {"A) Vitamin A", "B) Vitamin C", "C) Vitamin D", "D) Vitamin E"},
            {"A) 2,000 steps", "B) 5,000 steps", "C) 10,000 steps", "D) 15,000 steps"},
            {"A) White bread", "B) Blueberries", "C) Soda", "D) French Fries"},
            {"A) 4-5 hours", "B) 6-7 hours", "C) 7-9 hours", "D) 9-10 hours"},
            {"A) Trans fat", "B) Saturated fat", "C) Unsaturated fat", "D) Hydrogenated fat"},
            {"A) Weightlifting", "B) Running", "C) Yoga", "D) Cycling"},
            {"A) Helps build muscle", "B) Supports digestion and prevents constipation", "C) Provides energy", "D) Increases sugar levels"}
    };

    public static String[] correctAnswers = {
            "B) 2-3 liters",
            "A) Improved cardiovascular health",
            "B) A diet that includes all food groups in proper proportions",
            "C) Vitamin D",
            "C) 10,000 steps",
            "B) Blueberries",
            "C) 7-9 hours",
            "C) Unsaturated fat",
            "C) Yoga",
            "B) Supports digestion and prevents constipation"
    };

    public static QuestionSet getRandomQuestion() {
        Random random = new Random();
        int index = random.nextInt(question.length);

        return new QuestionSet(
                question[index],
                choices[index],
                correctAnswers[index]
        );
    }

    public static class QuestionSet {
        private final String question;
        private final String[] choices;
        private final String correctAnswer;

        public QuestionSet(String question, String[] choices, String correctAnswer) {
            this.question = question;
            this.choices = choices;
            this.correctAnswer = correctAnswer;
        }

        public String getQuestion() {
            return question;
        }

        public String[] getChoices() {
            return choices;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }
    }
}
