package com.example.tas_pam.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

  /**
   * An array of sample (dummy) items.
   */
  public static final List<Cafe> ITEMS = new ArrayList<Cafe>();

<<<<<<< HEAD
  /**
   * A map of sample (dummy) items, by ID.
   */
  public static final Map<String, Cafe> ITEM_MAP = new HashMap<String, Cafe>();

  private static final int COUNT = 25;

  static {
    // Add some sample items.
    for (int i = 1; i <= COUNT; i++) {
      addItem(createDummyItem(i));
    }
  }

  private static void addItem(Cafe item) {
    ITEMS.add(item);
    ITEM_MAP.put(item.id, item);
  }

  private static Cafe createDummyItem(int position) {
    return new Cafe(String.valueOf(position), makeDetails(position));
  }

  private static String makeDetails(int position) {
    StringBuilder builder = new StringBuilder();
    builder.append("Details about Item: ").append(position);
    for (int i = 0; i < position; i++) {
      builder.append("\nMore details information here.");
    }
    return builder.toString();
  }

  /**
   * A dummy item representing a piece of content.
   */
=======
>>>>>>> 41863978276096a804eb3d99815756484081e40e
  public static class Cafe {
    public final String id; // may used in view
    public final String establishment;
    public String hours;
    public String phone;
    public String address;
    public String description;

    public Cafe(String id, String establishment) {
      this.id = id;
      this.establishment= establishment;
    }

    @Override
    public String toString() {
      return establishment+ description;
    }
  }
}
