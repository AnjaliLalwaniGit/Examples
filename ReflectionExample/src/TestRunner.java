import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class TestRunner {

    private static final List<Class<?>> TESTS = List.of(CalculatorTest.class);

    public static void main(String[] args) throws Exception {

        List<String> passed = new ArrayList<>();
        List<String> failed = new ArrayList<>();

        for (Class<?> klass : TESTS) {
            // TODO: For each test class "klass", do the following:
            //        1. Ensure the class implements the UnitTest interface.
            //        2. Create an instance of the class and cast it to UnitTest.
            if(!UnitTest.class.isAssignableFrom(klass)){
               //UnitTest unitTestInstance=(UnitTest) klass.getConstructor().newInstance();
                throw  new IllegalArgumentException("Illegal Argument- Class must implement UnitTest");
            }

            // TODO: For each method that is annotated with @Test:
            //        1. Call beforeEachTest()
            //        2. Invoke the method annotated with @Test
            //        3. Call afterEachTest()
            //        4. Record the test results by adding getTestName(...) to either the "passed" list
            //           of the "failed" list.
            for(Method method: klass.getDeclaredMethods()){
                if(method.getAnnotation(Test.class) != null){
                    try{
                        UnitTest unitTestInstance=(UnitTest) klass.getConstructor().newInstance();
                        unitTestInstance.beforeEachTest();
                        method.invoke(unitTestInstance);
                        unitTestInstance.afterEachTest();
                        passed.add(getTestName(klass,method));
                    }catch (Throwable th){
                        failed.add(getTestName(klass,method));
                    }

                }
            }



           // unitTestClass.getAnnotations();

        }

        System.out.println("Passed tests: " + passed);
        System.out.println("FAILED tests: " + failed);
    }

    private static String getTestName(Class<?> klass, Method method) {
        return klass.getName() + "#" + method.getName();
    }
}

