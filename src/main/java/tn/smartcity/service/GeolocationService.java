package tn.smartcity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class GeolocationService {

    public String reverseGeocode(BigDecimal latitude, BigDecimal longitude) {
        // TODO: Implémenter Geocoding API (Google Maps ou autre)
        // Pour l'instant, retourne une adresse fictive
        return String.format("Lat: %s, Lng: %s", latitude, longitude);
    }

    public Double calculateDistance(BigDecimal lat1, BigDecimal lng1, 
                                   BigDecimal lat2, BigDecimal lng2) {
        // Formule de Haversine
        double earthRadius = 6371; // km
        
        double dLat = Math.toRadians(lat2.doubleValue() - lat1.doubleValue());
        double dLng = Math.toRadians(lng2.doubleValue() - lng1.doubleValue());
        
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1.doubleValue())) * 
                   Math.cos(Math.toRadians(lat2.doubleValue())) *
                   Math.sin(dLng / 2) * Math.sin(dLng / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return earthRadius * c;
    }
}
