package phonebook;

import java.util.TreeSet;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class MapPhoneBook implements PhoneBook {
	private HashMap<String, TreeSet<String>> book;

	public MapPhoneBook() {
		book = new HashMap<String, TreeSet<String>>();
	}

	@Override
	public boolean put(String name, String number) {
		// först kolla fall namnet inte finns ännu
		if (!book.containsKey(name)) {
			TreeSet<String> nummer = new TreeSet<String>();
			nummer.add(number);
			book.put(name, nummer);
			return true;
		}
		// Fall namnet finns och redan har numret
		if (book.get(name).contains(number)) {
			return false;
		}
		// sista alternativet är att namnet fanns men inte hade key.
		book.get(name).add(number);
		return true;

	}

	@Override
	public boolean remove(String name) {
		// Fall namnet inte ens finns ska inget hända
		if (!book.containsKey(name)) {
			return false;
		}
		// fall namnet finns ska den bort
		book.remove(name);
		return true;
	}

	@Override
	public boolean removeNumber(String name, String number) {
		if (book.containsKey(name) && book.get(name).contains(number)) {
			book.get(name).remove(number);
			return true;
		}
		return false;
	}

	@Override
	public Set<String> findNumbers(String name) {
		TreeSet<String> s = new TreeSet<String>();
		if (book.containsKey(name)) {
			return s = book.get(name);
		}
		return s;
		// TODO Auto-generated method stub

	}

	@Override
	public Set<String> findNames(String number) {
		TreeSet<String> s = new TreeSet<String>();
		for (Entry<String, TreeSet<String>> e : book.entrySet()) {
			if (e.getValue().contains(number)) {
				s.add(e.getKey());
			}
		}
		return new TreeSet<String>(s);
	}
	
	@Override
	public Set<String> names() {
		TreeSet<String> s = new TreeSet<String>();
		for(Entry<String,TreeSet<String>>e : book.entrySet()){
			s.add(e.getKey());
		}
		return s;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return names().size();
	}

}
