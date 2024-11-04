package dev.andrenascimento.sw_planet_api.common;

import dev.andrenascimento.sw_planet_api.domain.Planet;

public class PlanetConstants {
    public static final Planet PLANET = new Planet("name", "climate", "terrain");
    public static final Planet INVALID_PLANET = new Planet("", "", "");
    
    
}
