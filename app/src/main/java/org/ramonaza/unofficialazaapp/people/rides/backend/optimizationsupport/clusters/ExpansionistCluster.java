package org.ramonaza.unofficialazaapp.people.rides.backend.optimizationsupport.clusters;

import org.ramonaza.unofficialazaapp.people.backend.ContactInfoWrapper;
import org.ramonaza.unofficialazaapp.people.rides.backend.RidesOptimizer;

/**
 * A cluster based on expanding outwards from the location
 * of the initial contact.
 * Created by ilan on 10/2/15.
 */
public class ExpansionistCluster extends AlephCluster {

    public static final double RADIUS_ADDITION = 0.00619;
    private double[] center;
    private double innerRadius;

    public ExpansionistCluster(ContactInfoWrapper firstContact) {
        super(firstContact);
        center = new double[]{firstContact.getLatitude(), firstContact.getLongitude()};
    }


    @Override
    public boolean alephLiesInCluster(ContactInfoWrapper toCheck) {
        return (RidesOptimizer.distBetweenHouses(toCheck, center) <= innerRadius + RADIUS_ADDITION);
    }

    @Override
    public void recalculate() {
        innerRadius = 0;
        if (center == null) return;
        for (ContactInfoWrapper contact : contactsInCluster) {
            if (RidesOptimizer.distBetweenHouses(contact, center) > innerRadius) {
                innerRadius = RidesOptimizer.distBetweenHouses(contact, center);
            }
        }
    }

    public double[] getCenter() {
        return center;
    }

    public double getRadius() {
        return innerRadius + RADIUS_ADDITION;
    }
}