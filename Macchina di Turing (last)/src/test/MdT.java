package test;

import mdt.Machine;


public class MdT {

	public static void main(String[] args) {
		
		Machine mdt = new Machine("Multiplo di 3 o 7");
//		mdt.setStartState("s30");
//		mdt.setFinalState("fine");
//		mdt.addRule("; multiplo di 3 o 7");
//		mdt.addRule("s30 0 0 R s30");
//		mdt.addRule("s30 1 1 R s31");
//		mdt.addRule("s30 * V S fine");
//		mdt.addRule("s31 0 0 R s32");
//		mdt.addRule("s31 1 1 R s30");
//		mdt.addRule("s31 _ _ L b");
//		mdt.addRule("s32 0 0 R s34");
//		mdt.addRule("s32 1 1 R s35");
//		mdt.addRule("s32 _ _ L b");
//		mdt.addRule("s70 0 0 R s70");
//		mdt.addRule("s70 1 1 R s71");
//		mdt.addRule("s70 _ V S fine");
//		mdt.addRule("s71 0 0 R s72");
//		mdt.addRule("s71 1 1 R s73");
//		mdt.addRule("s72 0 0 R s74");
//		mdt.addRule("s72 1 1 R s75");
//		mdt.addRule("s73 0 0 R s76");
//		mdt.addRule("s73 1 1 R s70");
//		mdt.addRule("s74 0 0 R s71");
//		mdt.addRule("s74 1 1 R s72");
//		mdt.addRule("s75 0 0 R s73");
//		mdt.addRule("s75 1 1 R s74");
//		mdt.addRule("s76 0 0 R s75");
//		mdt.addRule("s76 1 1 R s76");
//		mdt.addRule("b * * L b");
//		mdt.addRule("b _ _ R s70");
		mdt.setStartState("0");
		mdt.addRule("; da decimale a binario");
		mdt.addRule("; elimino gli 0 iniziali");
		mdt.addRule("0 0 _ R 0");
		mdt.addRule("0 * * L 1");
		mdt.addRule("; appendo, prima dell'input senza gli 0 \"d\"");
		mdt.addRule("1 _ d R goToDec");
		mdt.addRule("; vado all'inizio del dec");
		mdt.addRule("goToD * * R goToD");
		mdt.addRule("goToD d d R goToDec");
		mdt.addRule("goToDec d d R goToDec");
		mdt.addRule("goToDec 0 d R goToDec");
		mdt.addRule("goToDec * * S s0");
		mdt.addRule("goToDec _ _ L out");
		mdt.addRule("; divido per 2 e ottengo il resto mod 2 r0 o r1");
		mdt.addRule("s0 0 0 R s0");
		mdt.addRule("s0 1 0 R s1");
		mdt.addRule("s0 2 1 R s0");
		mdt.addRule("s0 3 1 R s1");
		mdt.addRule("s0 4 2 R s0");
		mdt.addRule("s0 5 2 R s1");
		mdt.addRule("s0 6 3 R s0");
		mdt.addRule("s0 7 3 R s1");
		mdt.addRule("s0 8 4 R s0");
		mdt.addRule("s0 9 4 R s1");
		mdt.addRule("s0 _ _ L r0");
		mdt.addRule("s1 0 5 R s0");
		mdt.addRule("s1 1 5 R s1");
		mdt.addRule("s1 2 6 R s0");
		mdt.addRule("s1 3 6 R s1");
		mdt.addRule("s1 4 7 R s0");
		mdt.addRule("s1 5 7 R s1");
		mdt.addRule("s1 6 8 R s0");
		mdt.addRule("s1 7 8 R s1");
		mdt.addRule("s1 8 9 R s0");
		mdt.addRule("s1 9 9 R s1");
		mdt.addRule("s1 _ _ L r1");
		mdt.addRule("; riporto resto 0 in testa al bin");
		mdt.addRule("r0 * * L r0");
		mdt.addRule("r0 d d L r0bis");
		mdt.addRule("r0bis * * L r0bis");
		mdt.addRule("r0bis _ 0 R goToD");
		mdt.addRule("; riporto resto 1 in testa al bin");
		mdt.addRule("r1 * * L r1");
		mdt.addRule("r1 d d L r1bis");
		mdt.addRule("r1bis * * L r1bis");
		mdt.addRule("r1bis _ 1 R goToD");
		mdt.addRule("; preparo l'output e termino");
		mdt.addRule("out d _ L out");
		mdt.addRule("out 0 * L out");
		mdt.addRule("out 1 * L out");
		mdt.addRule("out _ _ R end-accept");
		
		String output = mdt.run("340282366920938463463374607431768211455");
		System.out.println(mdt.getEsito()+" ("+mdt.getSteps()+" steps):\n"+output);
//		System.out.println("\n\n");
//		System.out.println("MdT:\n"+mdt.printMachine());
	}

}
