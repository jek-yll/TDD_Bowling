package entity;

import org.example.entity.FallenKeel;
import org.example.entity.Frame;
import org.example.exception.NotReLaunchException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FrameTest {

    @Mock
    private FallenKeel fallenKeel;
    private Frame frame;
    void setUpStrike(){
        Mockito.when(fallenKeel.fallenKeelRandomNumber()).thenReturn(10);
    }
    void setUpSpare(){
        Mockito.when(fallenKeel.fallenKeelRandomNumber()).thenReturn(9);
        frame = new Frame(fallenKeel);
    }
    void setUpSerieStandard(){
        frame.setSeriesIsStandard(true);
    }
    void setUpSerieFinal(){
        frame = new Frame(fallenKeel);
        frame.setSeriesIsStandard(false);
    }

    @Test
    void testUpdateScoreWhenFirstLaunchInStandardSerie(){
        Mockito.when(fallenKeel.fallenKeelRandomNumber()).thenReturn(3);
        frame = new Frame(fallenKeel);
        frame.updateScore();
        Assertions.assertEquals(3, frame.getRoll().getScore());
    }

    @Test
    void testUpdateScoreWhenSecondLaunchInStandardSerie(){
        frame = new Frame(fallenKeel);
        Mockito.when(fallenKeel.fallenKeelRandomNumber()).thenReturn(4);
        frame.updateScore();
        frame.setRelance(true);
        Mockito.when(fallenKeel.fallenKeelRandomNumber()).thenReturn(4);
        frame.updateScore();
        Assertions.assertEquals(8, frame.getRoll().getScore());
    }

    @Test
    void testShouldNotPossibleToRelaunchAfterStrike(){
        frame = new Frame(fallenKeel);
        setUpStrike();
        Assertions.assertThrowsExactly(NotReLaunchException.class, () -> {
            frame.updateScore();
        });
    }

    @Test
    void testShouldNotPossibleToRelaunchAfterSecondLaunch3() {
        frame = new Frame(fallenKeel);

        Mockito.when(fallenKeel.fallenKeelRandomNumber()).thenReturn(4);
        frame.updateScore();

        Mockito.when(fallenKeel.fallenKeelRandomNumber()).thenReturn(4);
        frame.updateScore();

        Assertions.assertThrowsExactly(NotReLaunchException.class, () -> {
            frame.updateScore();
        });
    }

    @Test
    void testStrikeWhenFinalSeriesWeCanLaunchAgain(){
        setUpSerieFinal();
        setUpStrike();
        frame.updateScore();
        Assertions.assertTrue(frame.isRelance());

    }

    @Test
    void WhenFirstLaunchIsStrikeScoreOfSecondLaunchShouldBeCounted(){
        setUpSerieFinal();
        setUpStrike();
        frame.updateScore();
        Mockito.when(fallenKeel.fallenKeelRandomNumber()).thenReturn(4);
        frame.updateScore();
        Assertions.assertEquals(14,frame.getRoll().getScore());

    }

    @Test
    void WhenFirstLaunchIsStrikeScoreOfThirdLaunchShouldBeCounted(){
        setUpSerieFinal();
        setUpStrike();
        frame.updateScore();
        Mockito.when(fallenKeel.fallenKeelRandomNumber()).thenReturn(4);
        frame.updateScore();
        Mockito.when(fallenKeel.fallenKeelRandomNumber()).thenReturn(4);
        frame.updateScore();
        Assertions.assertEquals(18,frame.getRoll().getScore());

    }

    @Test
    void WhenSpareWeShouldLaunchASecondTime(){
        setUpSerieFinal();
        Mockito.when(fallenKeel.fallenKeelRandomNumber()).thenReturn(4);
        frame.updateScore();
        Mockito.when(fallenKeel.fallenKeelRandomNumber()).thenReturn(6);
        frame.updateScore();
        Assertions.assertTrue(frame.isRelance());
    }



}
