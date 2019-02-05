import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by mtumilowicz on 2019-02-05.
 */
public class AccessingFieldsTest {
    @Test
    public void accessing_int() throws NoSuchFieldException, IllegalAccessException {
        var concatenated = X.class.getDeclaredField("count")
                .getInt(new X());

        assertThat(concatenated, is(0));
    }

    @Test
    public void accessing_string() throws NoSuchFieldException, IllegalAccessException {
        var concatenated = (String) X.class.getDeclaredField("name")
                .get(new X());

        assertThat(concatenated, is("string"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void accessing_badType() throws NoSuchFieldException, IllegalAccessException {
        X.class.getDeclaredField("name").getInt(new X());
    }

    @Test(expected = IllegalAccessException.class)
    public void accessing_private() throws NoSuchFieldException, IllegalAccessException {
        X.class.getDeclaredField("privateField").get(new X());
    }
}
