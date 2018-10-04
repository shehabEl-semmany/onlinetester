package eg.edu.alexu.csd;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {

	/**
	 * This class is copied and compiled again with each test
	 * Change it to produce debug output
	 */
	private static final boolean Debug = false;
	private static final int max_length = 100;
	
	private static Class<?> implementation;
    private static String resourceDir;
    
    private static String showError(Throwable e) throws IOException {
    	 if(e==null)
             return "Error!";
    	e.printStackTrace();
        StringBuffer buffer = new StringBuffer();
        if (Debug)
        	buffer.append("\t\t\tError: " + e + " " + e.getMessage());
        else
        	buffer.append("\t\t\tError: " + e);
        if (Debug) {
        	for (StackTraceElement trace : e.getStackTrace()) {
				buffer.append("\n" + trace.getClassName() + "."
                        + trace.getMethodName() + "(): Line "
                        + trace.getLineNumber());
        	}
        } else {
        	if (implementation != null) {
        		for (StackTraceElement trace : e.getStackTrace()) {
        			if (trace.getClassName().equals(implementation.getName())) {
        				buffer.append("\n" + trace.getClassName() + "."
                                + trace.getMethodName() + "(): Line "
                                + trace.getLineNumber());
        			}
                }
        	}
        }
        return buffer.toString().replaceAll("\\n", "\n\t\t\t\t");
    }
    
    public static Object getImplementationInstance(){
        try {
            for(Constructor<?> constructor : implementation.getDeclaredConstructors()){
                if(constructor.getParameterTypes().length == 0){
                    constructor.setAccessible(true);
                    return constructor.newInstance((java.lang.Object[])null);
                }
            }
        } catch (Throwable e) {
        }
        return null;
    }

    public static Object getImplementationInstance(Object arg){
        try {
            for(Constructor<?> constructor : implementation.getDeclaredConstructors()){
                if(constructor.getParameterTypes().length == 1){
                    constructor.setAccessible(true);
                    return constructor.newInstance(arg);
                }
            }
        } catch (Throwable e) {
        }
        return null;
    }
    
    public static String getResourceDir(){
        return resourceDir;
    }
    
    public static String[] run(String resourceDir, String testPackage, List<String> classes) throws Throwable{
    	System.out.println("Starting the test!");
        TestRunner.resourceDir = resourceDir;
        StringBuffer log = new StringBuffer();
        List<Class<?>> tests = new LinkedList<Class<?>>();
        List<String> displayClasses = new LinkedList<String>();
        String project = testPackage.substring(0, testPackage.indexOf(".test")) +
                testPackage.substring(testPackage.lastIndexOf("."));
        try {
            for(String clazz : classes){
                Class<?> impl = Class.forName(clazz);
                if(impl.getName().startsWith(testPackage))
                    tests.add(impl);
                if (clazz.startsWith(project))
                    displayClasses.add(clazz);
            }
        } catch (Throwable e) {
        	log.append("{{{Please fix all compilation errors above first!").append("\n");
            log.append("Failed to create the test classes! Check if we compiled the correct project.").append("\n");
            log.append("Please review the checkedout 'Sources' list above for the detected source-code.").append("\n");
            log.append("\t").append(showError(e)).append("\n}}}");
            return new String[]{ "Compile", log.toString(),  "" };
        }
        
        log.append("UnitTests: " + (tests.isEmpty() ? "none!" : Arrays.toString(tests.toArray()))).append("\n");
        log.append("Classes: " + (displayClasses.isEmpty() ? "none!" : Arrays.toString(displayClasses.toArray()))).append("\n");
        
        int failed = 0;
        int total = 0;
        int numOfTestsPerClass = 0;
        Set<String> implementations = new HashSet<String>();
        for(Class<?> testClass : tests){
            Class<?> specs = null;
            try {
                Method method = null;
                try{
                    method = testClass.getMethod("getSpecifications");
                    // count number of test cases
                    Method[] methods = testClass.getDeclaredMethods();
                    numOfTestsPerClass = 0;
                    for (Method m : methods) {
                        Annotation[] annotations = m.getDeclaredAnnotations();
                        for (Annotation annotation : annotations)
                            if (annotation.annotationType().toString().equals("interface org.junit.Test"))
                                numOfTestsPerClass++;
                    }
                    total += numOfTestsPerClass;
                }catch(Throwable e){
                    continue;
                }
                log.append("\n\nRunning UnitTest: <<<" + testClass.getName()).append(">>>\n");
                specs = (Class<?>)method.invoke(null);
                log.append("\tInterface: " + specs.getName()).append("\n");
            } catch (Throwable e) {
                log.append("\tFailed to find the specification interface!").append("\n");
                log.append("\t\t").append(showError(e)).append("\n");
                return new String[] { "NoSpec", log.toString(),  "" };
            }
            
            // Getting an instance from student work
            List<Class<?>> impls = new LinkedList<Class<?>>();
            try {
                for(String clazz : classes) {
                    Class<?> impl = Class.forName(clazz);
                    if (specs.isAssignableFrom(impl)) {
                    	 if(!impl.isInterface() && !Modifier.isAbstract(impl.getModifiers())) {
                             impls.add(impl);
                         }
                    }
                }
                log.append("\tImplementations: " + (impls.isEmpty() ? "{{{none!}}}" : Arrays.toString(impls.toArray()))).append("\n");
                if (impls.isEmpty()) failed += numOfTestsPerClass; 
            } catch (Throwable e) {
                log.append("\t{{{Failed to create the implementation class!").append("\n");
                log.append("\t\t").append(showError(e)).append("}}}\n");
                return new String[] { "NoImpl", log.toString(),  "" };
            }
            
            // Test student work
            for(Class<?> impl : impls) {
                //System.err.println("Impl: " + impl.getName());
                log.append("\tTesting Implementation: " + impl.getName()).append("\n");
                implementations.add(impl.getName());
                implementation = impl;
                JUnitCore junit = new JUnitCore();
                Result result = junit.run(testClass);
                log.append("\t\t{{{Failed: " + result.getFailureCount()).append("\n");
                int i=0;
                for(Failure f : result.getFailures()){
                    log.append("\n\n\t\t\tFailure " + ++i + ":\n");
                    if (Debug) {
                    	log.append("\t\t\t" + f.getTestHeader() + "[");
                    	if (f.getMessage() != null)
                    		log.append(f.getMessage().replaceAll("<|>", " ") + "]").append("\n");
                    	else
                    		log.append(f.getMessage() + "]").append("\n");
                    }
                    else {
                    	log.append("\t\t\t" + f.getTestHeader() + "[");
                    	String msg = f.getMessage();
                    	if (msg != null && msg.length() <= max_length)
                    	{	
	                    	msg = msg.replaceAll("<|>", " ");
	                    	if (msg.contains("Exception:"))
	                    		msg = msg.substring(0, msg.indexOf("Exception:")) + "Exception";
	                    	log.append(msg + "]").append("\n");
                    	}
                    	else
                    		log.append("null" + "]").append("\n");
                    	
                    }
                    if (Debug)
                    	log.append("\t\t\t" + f.getTrace().replaceAll("\\r\n|\\n\\r|\\r|\\n", "\n\t\t\t"));
                    else {
                    	String[] traceArray = ("\t\t\t" + f.getTrace().
                    			replaceAll("\\r\n|\\n\\r|\\r|\\n", "\n\t\t\t")).split("\n");
                    	log.append(Arrays.stream(traceArray).filter(x -> x.contains("Test.java") || 
                    			x.contains(impl.getName()) || (x.endsWith("Exception") && x.length() <= max_length)).
                    			collect(Collectors.joining("\n")));
                    }
                    log.append("\n");
                }
                log.append("}}}");
                failed += result.getFailureCount();
                // total += result.getRunCount();
//              log.append("\t\tIgnore: " + result.getIgnoreCount()).append("\n");
                log.append("\t\t[[[Total : " + result.getRunCount()).append("]]]\n");
            }
        }
        //System.err.println("Total tests for " + testPackage + ": " + total);
        String impls = "";
        for(String impl : implementations)
            impls += impl + ";";
        if (failed == total)
            failed = total = 0;
        return new String[] { ((total-failed) < 0? 0: (total-failed)) + "/" + total, log.toString(),  impls};
    }
    
    public static void fail(String message, Throwable throwable) {
        try {
            StringBuffer log = new StringBuffer();
            if(message!=null)
                log.append(message).append("\n");
            if(throwable!=null) {
            	/*
                Throwable cause = throwable.getCause();
                if(cause!=null)
                    log.append(showError(cause));
                //*/
                log.append(showError(throwable));
            }
            Assert.fail(log.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
