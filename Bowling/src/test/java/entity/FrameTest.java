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
        frame = new Frame(fallenKeel);
    }
    void setUpSpare(){
        Mockito.when(fallenKeel.fallenKeelRandomNumber()).thenReturn(9);
        frame = new Frame(fallenKeel);
    }
    void setUpSerieStandard(){
        frame.setSeriesIsStandard(true);
    }
    void setUpSerieFinal(){
        frame.setSeriesIsStandard(false);
    }

    @Test
    void testUpdateScoreWhenFirstLaunchInStandardSerie(){
        Mockito.when(fallenKeel.fallenKeelRandomNumber()).thenReturn(3);
        frame = new Frame(fallenKeel);
        setUpSerieStandard();
        frame.updateScore();
        Assertions.assertEquals(3, frame.getRoll().getScore());
    }

    @Test
    void testUpdateScoreWhenSecondLaunchInStandardSerie(){
        frame = new Frame(fallenKeel);
        Mockito.when(fallenKeel.fallenKeelRandomNumber()).thenReturn(4);
        setUpSerieStandard();
        frame.updateScore();
        frame.setRelance(true);
        Mockito.when(fallenKeel.fallenKeelRandomNumber()).thenReturn(4);
        frame.updateScore();
        Assertions.assertEquals(8, frame.getRoll().getScore());
    }

    @Test
    void testShouldNotPossibleToRelaunchAfterStrike(){
        setUpStrike();
        setUpSerieStandard();
        Assertions.assertThrowsExactly(NotReLaunchException.class, () -> {
            frame.updateScore();
        });
    }

    @Test
    void testShouldNotPossibleToRelaunchAfterSecondLaunch(){
        frame = new Frame(fallenKeel);
        Mockito.when(fallenKeel.fallenKeelRandomNumber()).thenReturn(4);
        setUpSerieStandard();
        frame.setRelance(true);
        Mockito.when(fallenKeel.fallenKeelRandomNumber()).thenReturn(4);
        frame.setRelance(false);
        Mockito.when(fallenKeel.fallenKeelRandomNumber()).thenReturn(4);
        Assertions.assertThrowsExactly(NotReLaunchException.class, () -> {
            frame.updateScore();
        });
    }

    @Test
    void testShouldNotPossibleToRelaunchAfterSecondLaunch2(){
        frame = new Frame(fallenKeel);
        Mockito.when(fallenKeel.fallenKeelRandomNumber()).thenReturn(4);
        setUpSerieStandard();
        frame.setRelance(true);
        Mockito.when(fallenKeel.fallenKeelRandomNumber()).thenReturn(4);
        Assertions.assertFalse(frame.isRelance());
    }


}
