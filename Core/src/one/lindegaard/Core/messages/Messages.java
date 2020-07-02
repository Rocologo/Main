package one.lindegaard.Core.messages;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import one.lindegaard.Core.Core;
import one.lindegaard.Core.Strings;

public class Messages {

	private Plugin plugin;

	private File dataFolder;
	private String datapath = "";

	public Messages(Plugin plugin) {
		this.plugin = plugin;
		datapath = plugin.getDataFolder().getParent() + "/BagOfGold";
		dataFolder = new File(datapath);
		exportDefaultLanguages(plugin);
	}

	private static Map<String, String> mTranslationTable;
	private static String[] mValidEncodings = new String[] { "UTF-16", "UTF-16BE", "UTF-16LE", "UTF-8", "ISO646-US" };
	private static final String PREFIX = ChatColor.GOLD + "[BagOfGoldCore]" + ChatColor.RESET;
	private static String[] sources = new String[] { "bagofgoldcore_en_US.lang", "bagofgoldcore_hu_HU.lang", "bagofgoldcore_pl_PL.lang",
			"bagofgoldcore_ru_RU.lang", "bagofgoldcore_zh_CN.lang" };

	public void exportDefaultLanguages(Plugin plugin) {
		File folder = new File(dataFolder, "lang");
		if (!folder.exists())
			folder.mkdirs();

		for (String source : sources) {
			File dest = new File(folder, source);
			if (!dest.exists()) {
				Bukkit.getConsoleSender().sendMessage(PREFIX + " Creating language file " + source + " from JAR.");
				InputStream is = plugin.getResource("lang/" + source);
				String outputFile = datapath + "/lang/" + source;
				try {
					Files.copy(is, Paths.get(outputFile));
					File file = new File(outputFile);
					sortFileOnDisk(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				if (!injectChanges(plugin.getResource("lang/" + source), new File(dataFolder, "lang/" + source))) {
					InputStream is = plugin.getResource("lang/" + source);
					String outputFile = datapath + "/lang/" + source;
					try {
						Files.copy(is, Paths.get(outputFile));
						File file = new File(outputFile);
						sortFileOnDisk(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			mTranslationTable = loadLang(dest);
		}
	}

	private static boolean injectChanges(InputStream inJar, File onDisk) {
		try {
			Map<String, String> source = loadLang(inJar, "UTF-8");
			Map<String, String> dest = loadLang(onDisk);

			if (dest == null)
				return false;

			HashMap<String, String> newEntries = new HashMap<String, String>();
			for (String key : source.keySet()) {
				if (!dest.containsKey(key)) {
					newEntries.put(key, source.get(key));
				}
			}

			if (!newEntries.isEmpty()) {
				BufferedWriter writer = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(onDisk, true), StandardCharsets.UTF_8));
				for (Entry<String, String> entry : newEntries.entrySet())
					writer.append("\n" + entry.getKey() + "=" + entry.getValue());
				writer.close();
				sortFileOnDisk(onDisk);
				Bukkit.getConsoleSender()
						.sendMessage(PREFIX + " Updated " + onDisk.getName() + " language file with missing keys");
			}

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private static boolean sortFileOnDisk(File onDisk) {
		try {
			Map<String, String> source = loadLang(onDisk);
			source = sortByKeys(source);
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(onDisk, false), StandardCharsets.UTF_8));
			for (Entry<String, String> entry : source.entrySet()) {
				writer.append("\n" + entry.getKey() + "=" + entry.getValue());
			}
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private static Map<String, String> loadLang(InputStream stream, String encoding) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, encoding));

			while (reader.ready()) {
				String line = reader.readLine();
				if (line == null)
					continue;
				int index = line.indexOf('=');
				if (index == -1)
					continue;

				String key = line.substring(0, index).trim();
				String value = line.substring(index + 1).trim();

				map.put(key, value);
			}
			reader.close();
		} catch (Exception e) {
			Bukkit.getServer().getConsoleSender()
					.sendMessage(PREFIX + " Error reading the language file. Please check the format.");
		}

		return map;
	}

	private static Pattern mDetectEncodingPattern = Pattern.compile("^[a-zA-Z\\.\\-0-9_]+=.+$");

	private static String detectEncoding(File file) throws IOException {
		for (String charset : mValidEncodings) {
			FileInputStream input = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(input, charset));
			String line = null;
			boolean ok = true;

			while (reader.ready()) {
				line = reader.readLine();
				if (line == null || line.trim().isEmpty())
					continue;

				if (!mDetectEncodingPattern.matcher(line.trim()).matches())
					ok = false;
			}

			reader.close();

			if (ok)
				return charset;
		}

		return "UTF-8";
	}

	private static Map<String, String> loadLang(File file) {
		Map<String, String> map;

		try {
			String encoding = detectEncoding(file);
			if (encoding == null) {
				FileInputStream input = new FileInputStream(file);
				Bukkit.getConsoleSender()
						.sendMessage(PREFIX + " Could not detect encoding of lang file. Defaulting to UTF-8");
				map = loadLang(input, "UTF-8");
				input.close();
			}

			FileInputStream input = new FileInputStream(file);
			map = loadLang(input, encoding);
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return map;
	}

	public void setLanguage(String lang) {
		File file = new File(dataFolder, "lang/" + lang);
		if (!file.exists()) {
			Bukkit.getConsoleSender().sendMessage(PREFIX
					+ " Language file does not exist. Creating a new file based on en_US. You need to translate the file yourself.");
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (file.exists()) {
			InputStream resource = plugin.getResource("lang/bagofgoldcore_en_US.lang");
			injectChanges(resource, file);
			mTranslationTable = loadLang(file);
		} else {
			Bukkit.getConsoleSender().sendMessage(PREFIX + " Could not read the language file:" + file.getName());
		}

		if (mTranslationTable == null) {
			mTranslationTable = new HashMap<String, String>();
			Bukkit.getConsoleSender().sendMessage(PREFIX + " Creating empty translation table.");
		}
	}

	private static String getStringInternal(String key) {
		String value = mTranslationTable.get(key);

		if (value == null) {
			Bukkit.getConsoleSender().sendMessage(PREFIX + " mTranslationTable has not key: " + key.toString());
			throw new MissingResourceException("", "", key);
		}

		return value.trim();
	}

	private static Pattern mPattern;

	/**
	 * Gets the message and replaces specified values
	 * 
	 * @param key    The message key to find
	 * @param values these are key-value pairs, they should be like: {key1, value1,
	 *               key2, value2,..., keyN,valueN}. keys must be strings
	 */
	public String getString(String key, Object... values) {
		try {
			if (mPattern == null)
				mPattern = Pattern.compile("\\$\\{([\\w\\.\\-]+)\\}");

			HashMap<String, Object> map = new HashMap<String, Object>();

			String name = null;
			for (Object value : values) {
				if (name == null)
					name = (String) value; // This must be a string
				else {
					map.put(name, value);
					name = null;
				}
			}

			String str = getStringInternal(key);
			Matcher m = mPattern.matcher(str);

			String output = str;

			while (m.find()) {
				name = m.group(1);
				Object replace = map.get(name);
				if (replace != null)
					output = output.replaceAll("\\$\\{" + name + "\\}", Matcher.quoteReplacement(replace.toString()));
			}

			return Strings.convertColors(ChatColor.translateAlternateColorCodes('&', output));
		} catch (MissingResourceException e) {
			Bukkit.getConsoleSender().sendMessage(PREFIX + " Could not find key: " + key.toString());
			return key;
		}
	}

	public String getString(String key) {
		try {
			return Strings.convertColors(ChatColor.translateAlternateColorCodes('&', getStringInternal(key)));
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Show debug information in the Server console log
	 * 
	 * @param message
	 * @param args
	 */
	public void debug(String message, Object... args) {
		if (Core.getConfigManager().debug) {
			Bukkit.getServer().getConsoleSender().sendMessage(PREFIX + " [Debug] " + String.format(message, args));
		}
	}

	private static Map<String, String> sortByKeys(Map<String, String> map) {
		SortedSet<String> keys = new TreeSet<String>(map.keySet());
		Map<String, String> sortedHashMap = new LinkedHashMap<String, String>();
		for (String it : keys) {
			sortedHashMap.put(it, map.get(it));
		}
		return sortedHashMap;
	}

	private static boolean isEmpty(String message) {
		message = ChatColor.stripColor(message);
		return message.isEmpty();
	}

}
