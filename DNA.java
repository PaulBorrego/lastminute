import java.util.*;

public class DNA {
  //Fields
  private String seq;
  //Constructor
  public DNA(String s) {
    this.seq = s.toUpperCase();
  }

  //Methods (I recommend uncommenting each method as you write them and 
  //testing each one before you move on to the next)
  
  public int length() {return seq.length();} 
  public String toString() {return seq;}

  public DNA reverseComplement() {
	String comp = "";
	String temp;
  	for (int i = 0; i < seq.length(); i++) {
		temp = seq.substring(i,i+1);

		if (temp.equals("A")) {comp = "T" + comp;}
		else if (temp.equals("T")) {comp ="A" + comp;}
		else if (temp.equals("C")) {comp ="G" + comp;}
		else {comp = "C" + comp;}
	}
	return new DNA(comp);
  }
  public DNA getSubsequence(int start, int end) {return new DNA(seq.substring(start,end));}

  public int hammingDistance(DNA other) {
	  int distance = 0; 
	  for (int i = 0; i < seq.length(); i++) {
		  if (!(this.seq.substring(i,i+1).equals(other.seq.substring(i,i+1)))) {distance++;}
	  }
  	  return distance;
  }
  public int hammingDistance(String other) {
  	  int distance = 0; 
	  for (int i = 0; i < seq.length(); i++) {
		  if (!(this.seq.substring(i,i+1).equals(other.substring(i,i+1)))) {distance++;}
	  }
  	  return distance;
  }
  
  public ArrayList<Integer> findExactOccurences(DNA kmer) {
  	ArrayList<Integer> occ = new ArrayList<Integer>();
	for (int i = 0; i <= (seq.length() - kmer.seq.length());i++) {
		if (this.seq.substring(i,i+ kmer.seq.length()).equals(kmer.seq)) {
			occ.add(i);
		}
	}
	return occ;
  }
  public ArrayList<Integer> findExactOccurences(String kmer) {
  
  	ArrayList<Integer> occ = new ArrayList<Integer>();
	for (int i = 0; i <= (seq.length() - kmer.length());i++) {
		if (this.seq.substring(i,i+ kmer.length()).equals(kmer)) {
			occ.add(i);	
		}
	}

	return occ;
  }
  
  public ArrayList<Integer> findApproximateOccurences(DNA kmer, int d) {
  	ArrayList<Integer> occ = new ArrayList<Integer>();

	for (int j = 0; j < (seq.length() - kmer.seq.length()); j++) {
		Integer a = approx(kmer.seq,d,j);
		if (a != -1) {occ.add(a);}
	}

  	return occ;
  }

  private Integer approx(String kmer, int d, int j) {
	int allow = 0;
	for (int i = 0; i < kmer.length();i++) {
		if (!(seq.substring(j + i,j + i + 1).equals(kmer.substring(i,i+1)))) {
			allow++;
			if (allow > d) {return -1;}
		}
	}
	return j;
  }


  public ArrayList<Integer> findApproximateOccurences(String kmer, int d) {
	ArrayList<Integer> occ = new ArrayList<Integer>();

	for (int j = 0; j < (seq.length() - kmer.length()); j++) {
		Integer a = approx(kmer,d,j);
		if (a != -1) {occ.add(a);}
	}

  	return occ;
  }
  
  public int countExactRepeats(DNA kmer) {
	  return findExactOccurences(kmer).size();
	  }
  public int countExactRepeats(String kmer) {
	  return findExactOccurences(kmer).size();
  }

  public int countApproximateRepeats(String kmer, int d) {
		ArrayList<Integer> a =  findApproximateOccurences(kmer,d);
		int count = 0;
		for (int i = 0; i < (a.size() - 1);i++) {
			if (a.get(i) == (a.get(i + 1) - 1)) {count++;}
		}
		return count;
	}
  public int countApproximateRepeats(DNA kmer, int d) {
	ArrayList<Integer> a =  findApproximateOccurences(kmer,d);
	int count = 0;
	for (int i = 0; i < (a.size() - 1);i++) {
		if (a.get(i) == (a.get(i + 1) - 1)) {count++;}}
	return count;
}
  
  public ArrayList<DNA> findOpenReadingFrames() {
  	  ArrayList<Integer> first = findExactOccurences("ATG");
	  ArrayList<DNA> ret = new ArrayList<DNA>();
	  
	  String hold = seq;
	  boolean check;

	  ArrayList<Integer> temp = findExactOccurences("TAA");
	  temp.addAll(findExactOccurences("TAG"));
	  temp.addAll(findExactOccurences("TGA"));

		temp.sort(Comparator.naturalOrder());
	  
	  for (int i = 0; i < first.size(); i++) {
		check = true;
		for (int j = 0; (j < temp.size()) && (check);j++) {
			if (first.get(i) < temp.get(j) + 6){
				if((first.get(i) - temp.get(j)) % 3 == 0) {
					ret.add(new DNA	(seq.substring(first.get(i),temp.get(j)+3)));
					check = false;
				}
			}
		}
	  }


	  //reverses
		seq = reverseComplement().seq;
 		first = findExactOccurences("ATG");
	  

	  temp = findExactOccurences("TAA");
	  temp.addAll(findExactOccurences("TAG"));
	  temp.addAll(findExactOccurences("TGA"));

	temp.sort(Comparator.naturalOrder());

	  for (int i = 0; i < first.size(); i++) {
		check = true;
		for (int j = 0; (j < temp.size()) && (check);j++) {
			if (first.get(i) < temp.get(j) + 6){
				if((first.get(i) - temp.get(j)) % 3 == 0) {
					ret.add(new DNA	(seq.substring(first.get(i),temp.get(j)+3)));
					check = false;
				}
			}
		}
	  }
	  seq = hold;
	  return ret;
  }
}
