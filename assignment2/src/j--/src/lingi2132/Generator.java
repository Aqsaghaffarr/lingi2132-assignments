package lingi2132;

import static jminusminus.CLConstants.*;

import java.util.ArrayList;

import jminusminus.CLEmitter;

/**
 * This class programmatically generates the class file for the 
 * following Java application using CLEmitter:
 * package packageOfClassToGenerate;
 *
 * public class ClassToGenerate {
 *     public static int gcd(int a, int b) {
 * 		   while (b != 0) {
 * 			   if (a > b) {
 * 				   a = a - b;
 * 			   } else {
 * 				   b = b - a;
 * 			   }
 * 		   }
 * 		   return a;
 * 	   }
 * }
 */
public class Generator extends GlobalGenerator {
	protected String outputDir;
	
	public Generator(String outputDir) {
		super(outputDir);
		this.outputDir = outputDir;
	}
	
	public void generateClass() {
        CLEmitter e = new CLEmitter(true);
        e.destinationDir(outputDir);
        ArrayList<String> accessFlags = new ArrayList<>();

        // Add ClassToGenerate class.
        accessFlags.add("public");
        e.addClass(accessFlags, "packageOfClassToGenerate/ClassToGenerate", "java/lang/Object", null, false);
        
        // Add the implicit no-arg constructor ClassToGenerate() to ClassToGenerate.
        accessFlags.clear();
        accessFlags.add("public");
        e.addMethod(accessFlags, "<init>", "()V", null, false);
        e.addNoArgInstruction(ALOAD_0);
        e.addMemberAccessInstruction(INVOKESPECIAL, "java/lang/Object",
                "<init>", "()V");
        e.addNoArgInstruction(RETURN);
        
        // Add gcd() method to ClassToGenerate.
        accessFlags.clear();
        accessFlags.add("public");
        accessFlags.add("static");
        e.addMethod(accessFlags, "gcd", "(II)I", null, false);
        e.addLabel("startLabel"); // Start of while.
        e.addNoArgInstruction(ILOAD_1); // Load b.
        e.addBranchInstruction(IFEQ, "outLabel"); // If b == 0, leave while.
        e.addNoArgInstruction(ILOAD_0); // Load a.
        e.addNoArgInstruction(ILOAD_1); // Load b.
        e.addBranchInstruction(IF_ICMPLE, "elseLabel"); // If a <= b go to else.
        e.addNoArgInstruction(ILOAD_0); // Load a.
        e.addNoArgInstruction(ILOAD_1); // Load b.
        e.addNoArgInstruction(ISUB); // a - b.
        e.addNoArgInstruction(ISTORE_0); // Store result in a.
        e.addBranchInstruction(GOTO, "startLabel"); // Go to start of loop.
        e.addLabel("elseLabel"); // Else.
        e.addNoArgInstruction(ILOAD_1); // Load b.
        e.addNoArgInstruction(ILOAD_0); // Load a.
        e.addNoArgInstruction(ISUB); // b - a.
        e.addNoArgInstruction(ISTORE_1); // Store result in b.
        e.addBranchInstruction(GOTO, "startLabel"); // Go to start of loop.
        e.addLabel("outLabel"); // End of while.
        e.addNoArgInstruction(ILOAD_0); // Load a.
        e.addNoArgInstruction(IRETURN); // Return a.
        
        // Write ClassToGenerate.class to file system.
        if(!e.errorHasOccurred()) {
            e.write();
        }
	}

	public static void main(String[] args) {
	    GlobalGenerator gen;
        if (args.length == 0) {
            gen = new Generator("bin");
        } else {
            gen = new Generator(args[0]);
        }
        gen.generateClass();
    }
}