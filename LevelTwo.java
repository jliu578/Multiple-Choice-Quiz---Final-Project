import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class LevelTwo extends JFrame {
    private JLabel questionLabel;
    private JButton[] answerButtons;
    private JLabel correctAnswersLabel;
    private ArrayList<Question> questions;
    private int currentQuestionIndex;
    private int correctAnswers;
    private LevelSwitcher levelSwitcher;

    public LevelTwo(LevelSwitcher levelSwitcher) {
        this.levelSwitcher = levelSwitcher;
        setTitle("Level Two");
        setPreferredSize(new Dimension(400, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        questionLabel = new JLabel("Question will appear here", SwingConstants.CENTER);
        add(questionLabel, BorderLayout.NORTH);
        
        JPanel answersPanel = new JPanel(new GridLayout(2, 2));
        answerButtons = new JButton[4];
        
        for (int i = 0; i < 4; i++) {
            answerButtons[i] = new JButton();
            answerButtons[i].addActionListener(new AnswerButtonListener());
            answersPanel.add(answerButtons[i]);
        }
        
        add(answersPanel, BorderLayout.CENTER);
        correctAnswersLabel = new JLabel("Correct Answers: 0", SwingConstants.CENTER);
        add(correctAnswersLabel, BorderLayout.SOUTH);
        currentQuestionIndex = 0;
        correctAnswers = 0;
        generateQuestions();
        showNextQuestion();
        pack();
        setVisible(true);
    }

    private void generateQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("What is equal to square root of 169?", new String[]{"A: -16", "B: 15", "C: -17", "D: 13"}, "D: 13"));
        questions.add(new Question("If sin x = 1/2, find x.", new String[]{"A: π/2", "B: π/6", "C: π/4", "D: 5π/6"}, "D: 5π/6"));
        questions.add(new Question("Find cot 3π/4", new String[]{"A: 1/2", "B: -1/2", "C: 1", "D: -1"}, "D: -1"));
        // Add more questions to reach a total of 10 for level two
    }

    private void showNextQuestion() {
        System.out.println("Current Index: " + currentQuestionIndex);
        System.out.println("Questions Size: " + questions.size());
        if (currentQuestionIndex < questions.size()) {
            Question q = questions.get(currentQuestionIndex);
            questionLabel.setText(q.showQuestion());
            String[] choices = q.showChoices();
            for (int i = 0; i < 4; i++) {
                answerButtons[i].setText(choices[i]);
            }
        } else {
            if (correctAnswers >= 3) {
                levelSwitcher.startThree();
            } 
        }
    }

    private class AnswerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            Question currentQuestion = questions.get(currentQuestionIndex);
            String correctAnswer = currentQuestion.showCorrectAns();
            if (source.getText().equals(correctAnswer)) {
                correctAnswers++;
                JOptionPane.showMessageDialog(null, "Correct!");
                correctAnswersLabel.setText("Correct Answers: " + correctAnswers);
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect!");
            }
            currentQuestionIndex++;
            showNextQuestion();

            correctAnswersLabel.revalidate();
            correctAnswersLabel.repaint();
        }
    }
}
