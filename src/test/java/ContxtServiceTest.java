import com.ndustrialio.contxt.Contxt;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created by jmhunt on 6/22/17.
 */
public class ContxtServiceTest
{

    Contxt contxt;

    @Before
    public void setup()
    {
        contxt = new Contxt(System.getenv("CLIENT_ID"), System.getenv("CLIENT_SECRET"));
    }


    @Test
    public void runTest() throws IOException
    {
        Map<String, Object> response = contxt.getServiceConfiguration("64c6dde7-7830-47c1-a411-6c39c158ec79");

        response.forEach((k,v) -> System.out.println("config["+ k + "] = " + v));
    }

}
