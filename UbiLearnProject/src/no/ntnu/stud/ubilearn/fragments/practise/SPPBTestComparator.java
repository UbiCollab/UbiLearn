package no.ntnu.stud.ubilearn.fragments.practise;

import java.util.Comparator;

import no.ntnu.stud.ubilearn.models.SPPB;

public class SPPBTestComparator implements Comparator<SPPB>{

	@Override
	public int compare(SPPB lhs, SPPB rhs) {
		if(lhs.getCreatedAt().before(rhs.getCreatedAt()))
			return -1;
		else if (rhs.getCreatedAt().before(lhs.getCreatedAt()))
			return 1;
		else
			return 0;
	}

}
