import java.util.Stack;

public class StringOps {

	/**
	 * Compares strings s1 and s2, and returns true if they are identical, false if
	 * different.
	 * 
	 * @param s1 First string
	 * @param s2 Second string
	 * @return Whether s1 and s2 are identical
	 */
	public static boolean equals(String s1, String s2) {
		// TODO: Fix bug! --
		//avoiding the corner case described in the assignment readMe
		for (int i = 0; i < Integer.min(s1.length(), s2.length()); i++) {
			//if either of the characters at this index are null 
			//(i.e the strings are of different lengths)
			if (s1.length() != s2.length()) {
				return false;
			} else if (s1.charAt(i) != s2.charAt(i)) {
				return false;
			}
			
		}
		return true;
	}

	/**
	 * Checks whether string s is in valid HTML format. Valid HTML is composed of
	 * nested HTML tags. In this method, we only check for <b> tags and <i> tags.
	 * Specifically if each <b> tag is matched by a corresponding </b> tag and each
	 * <i> tag is matched by a corresponding </i> tag.
	 * 
	 * @param s String containing HTML page
	 * @return Whether s is in valid HTML format (with matching <b> and <i> tags)
	 */
	public static boolean isValidHTML(String s) {
		// TODO: Fix bug!
		Stack<String> stack = new Stack<String>();
		for (int i = 0; i < s.length(); i++) {
			if (s.startsWith("<b>", i) || s.startsWith("<i>", i)) {
				stack.push(s.substring(i, i + 3));
			} else if (s.startsWith("</b>", i) || s.startsWith("</i>", i)) {
				if (stack.empty()) {
					return false;
				}
				if (s.startsWith("</b>", i)) {
					assert s.startsWith("</b>", i);
					String top = stack.pop();
					if (!top.equals("<b>")) {
						return false;
					}
				} else {
					assert s.startsWith("</i>", i);
					String top = stack.pop();
					if (!top.equals("<i>")) {
						return false;
					}
				}
			}
		}
		return stack.empty();
	}
}
