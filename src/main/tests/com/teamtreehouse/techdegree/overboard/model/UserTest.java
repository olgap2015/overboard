package com.teamtreehouse.techdegree.overboard.model;

import com.teamtreehouse.techdegree.overboard.exc.VotingException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class UserTest {

    private Board board;
    private User questioner;
    private User answerer;
    private User reader;
    private Question question;
    private Answer answer;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        board = new Board("Java");
        questioner = board.createUser("Olga");
        answerer = board.createUser("Craig");
        reader = board.createUser("Sam");
        question = questioner.askQuestion("What does AAA stand for in Unit Testing?");
        answer = answerer.answerQuestion(question, "It stands for Arrange, Act, Assert.");
    }

    @Test
    public void questionersReputationGoesUpIfQuestionIsUpvoted() {
        reader.upVote(question);

        assertEquals(5, questioner.getReputation());
    }

    @Test
    public void answerersReputationGoesUpIfAnswerIsUpvoted() {
        reader.upVote(answer);

        assertEquals(10, answerer.getReputation());
    }

    @Test
    public void answerersReputationGoesUpIfAnswerIsAccepted() {
        questioner.acceptAnswer(answer);

        assertEquals(15, answerer.getReputation());
    }

    @Test
    public void upvotingTheQuestionIsNotAllowedForAuthor() {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");

        questioner.upVote(question);
    }

    @Test
    public void upvotingTheAnswerIsNotAllowedForAuthor() {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");

        answerer.upVote(answer);
    }

    @Test
    public void downvotingTheQuestionIsNotAllowedForAuthor() {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");

        questioner.downVote(question);
    }
}