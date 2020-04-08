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
