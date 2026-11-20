package eus.birt.dam;

import java.awt.List;
import java.util.ArrayList;

public class NotebookColorMap {
	
	private static String[] colorList;
	
	public static String getColor(int color) {
	    switch(color) {
		    default:
		    case 0:
	        case 1:  return "#000000";
	        case 2:  return "#222121";
	        case 3:  return "#54514d";
	        case 4:  return "#89918f";
	        case 5:  return "#baab91";
	        case 6:  return "#d18e62";
	        case 7:  return "#d8795f";
	        case 8:  return "#a65944";
	        case 9:  return "#7a4435";
	        case 10: return "#2b1a19";
	        case 11: return "#562d2d";
	        case 12: return "#d63b3b";
	        case 13: return "#e2722d";
	        case 14: return "#f9b34a";
	        case 15: return "#f9c68b";
	        case 16: return "#ffffff";
	        case 17: return "#248c77";
	        case 18: return "#42757c";
	        case 19: return "#354b54";
	        case 20: return "#104a54";
	        case 21: return "#125c41";
	        case 22: return "#253f3b";
	        case 23: return "#548949";
	        case 24: return "#84ba72";
	        case 25: return "#cdd6b1";
	        case 26: return "#a3af38";
	        case 27: return "#869354";
	        case 28: return "#5d6131";
	        case 29: return "#896c39";
	        case 30: return "#f7d04f";
	        case 31: return "#f6dd92";
	        case 32: return "#f4e7c6";
	        case 33: return "#2ea7ba";
	        case 34: return "#84c9cc";
	        case 35: return "#b6cdcf";
	        case 36: return "#55a6e8";
	        case 37: return "#2c619e";
	        case 38: return "#20284c";
	        case 39: return "#6875ab";
	        case 40: return "#a3a4dd";
	        case 41: return "#d4c2e7";
	        case 42: return "#8b6693";
	        case 43: return "#784f7f";
	        case 44: return "#44304d";
	        case 45: return "#993d44";
	        case 46: return "#d3709e";
	        case 47: return "#e5a5a5";
	        case 48: return "#f2d8d2";
	    }
	}
	
	public static String[] getColors() {
		if (colorList != null) return colorList;
		ArrayList<String> colors = new ArrayList<String>();
		for (int i = 0; i <= 48; i++) {
			colors.add(getColor(i));
		}
		colorList = colors.toArray(new String[0]);
		return colorList;
	}

}
