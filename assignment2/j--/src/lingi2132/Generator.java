package lingi2132;

import java.util.ArrayList;
import jminusminus.CLEmitter;
import static jminusminus.CLConstants.*;

public class Generator extends BinomialCoefficientGenerator {
	public Generator (String outputDir) {
		super(outputDir);
	}

	public void generateClass() {
		CLEmitter emit = new CLEmitter(true);
		emit.destinationDir(outputDir);
		
		ArrayList<String> flags = new ArrayList<String>();

		flags.add("public");
		emit.addClass(flags, "packageOfClassToGenerate/ClassToGenerate", "java/lang/Object", null, false);

		flags.clear();

		flags.add("public");
		flags.add("static");

		emit.addMethod(flags, "binomialCoefficient", "(II)I", null, false);

		emit.addNoArgInstruction(ILOAD_1);
		emit.addNoArgInstruction(ICONST_0);
		emit.addBranchInstruction(IF_ICMPEQ, "if");
		emit.addNoArgInstruction(ILOAD_0);
		emit.addNoArgInstruction(ILOAD_1);
		emit.addBranchInstruction(IF_ICMPEQ, "if");

		emit.addNoArgInstruction(ILOAD_0);
		emit.addNoArgInstruction(ICONST_1);
		emit.addNoArgInstruction(ISUB);
		emit.addNoArgInstruction(ILOAD_1);
		emit.addMemberAccessInstruction(INVOKESTATIC, "packageOfClassToGenerate/ClassToGenerate", "binomialCoefficient", "(II)I");

		emit.addNoArgInstruction(ILOAD_0);
		emit.addNoArgInstruction(ICONST_1);
		emit.addNoArgInstruction(ISUB);
		emit.addNoArgInstruction(ILOAD_1);
		emit.addNoArgInstruction(ICONST_1);
		emit.addNoArgInstruction(ISUB);
		emit.addMemberAccessInstruction(INVOKESTATIC, "packageOfClassToGenerate/ClassToGenerate", "binomialCoefficient", "(II)I");

		emit.addNoArgInstruction(IADD);
		emit.addNoArgInstruction(IRETURN);

		emit.addLabel("if");
		emit.addNoArgInstruction(ICONST_1);
		emit.addNoArgInstruction(IRETURN);

		emit.write();

	}
}
