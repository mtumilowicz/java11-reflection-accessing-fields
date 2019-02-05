import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by mtumilowicz on 2019-02-05.
 */
public class AccessingFieldsTest {
    @Test
    public void accessing_int() throws NoSuchFieldException, IllegalAccessException {
        var count = X.class.getDeclaredField("count")
                .getInt(new X());

        assertThat(count, is(0));
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

    @Test
    public void set_int() throws NoSuchFieldException, IllegalAccessException {
        var x = new X();
        X.class.getDeclaredField("count")
                .setInt(x, 1);

        assertThat(x.count, is(1));
    }

    @Test(expected = IllegalAccessException.class)
    public void set_finalString() throws NoSuchFieldException, IllegalAccessException {
        var x = new X();
        X.class.getDeclaredField("name")
                .set(x, "set");

        assertThat(x.name, is("set"));
    }

    @Test
    public void set_notFinal() throws NoSuchFieldException, IllegalAccessException {
        var x = new X();
        X.class.getDeclaredField("notFinal")
                .set(x, "set");

        assertThat(x.notFinal, is("set"));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void set_wrongType() throws NoSuchFieldException, IllegalAccessException {
        X.class.getDeclaredField("notFinal")
                .setInt(new X(), 1);
    }
}
