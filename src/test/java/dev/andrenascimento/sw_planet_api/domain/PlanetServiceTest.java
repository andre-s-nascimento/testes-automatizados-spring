package dev.andrenascimento.sw_planet_api.domain;

import static dev.andrenascimento.sw_planet_api.common.PlanetConstants.INVALID_PLANET;
import static dev.andrenascimento.sw_planet_api.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

//@SpringBootTest(classes = PlanetService.class)
@ExtendWith(MockitoExtension.class)
public class PlanetServiceTest {
    // @Autowired

    @InjectMocks
    private PlanetService planetService;

    // @MockBean
    @Mock
    private PlanetRepository planetRepository;

    // operação_estado_retorno_esperado
    @Test
    public void createPlanet_WithValidData_ReturnsPlanet() {
        // Arrange - Preparar
        when(planetRepository.save(PLANET)).thenReturn(PLANET);

        // sut - system under test
        // Act - Executar
        Planet sut = planetService.create(PLANET);

        // Assert - Teste
        assertThat(sut).isEqualTo(PLANET);
    }

    @Test
    public void createPlanet_WithInvalidData_ThrowsException() {
        // Arrange - Preparar
        when(planetRepository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);

        // Act - Executar // Assert
        assertThatThrownBy(() -> planetService.create(INVALID_PLANET)).isInstanceOf(RuntimeException.class);

    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() {
        // Arrange - Preparar
        when(planetRepository.findById(anyLong())).thenReturn(Optional.of(PLANET));

        // sut - system under test
        // Act - Executar
        Optional<Planet> sut = planetService.get(1L);

        // Assert
        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET);
    }

    @Test
    public void getPlanet_ByNonExistingId_ReturnsEmpty() {
        // Arrange - Preparar
        when(planetRepository.findById(anyLong())).thenReturn(Optional.empty());

        // sut - system under test
        Optional<Planet> sut = planetService.get(1L);

        assertThat(sut).isEmpty();

        // Act - Executar - Assert
        // o spring com findById não lança essa exceção normalmente.
        assertThatThrownBy(
                () -> planetService.get(1L).get()).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet() {
        // Arrange - Preparar
        when(planetRepository.findByName(PLANET.getName())).thenReturn(Optional.of(PLANET));

        // sut - system under test
        // Act - Executar
        Optional<Planet> sut = planetService.getByName(PLANET.getName());

        // Assert
        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET);
    }

    @Test
    public void getPlanet_ByNonExistingName_ReturnsEmpty() {
        // Arrange - Preparar
        final String name = "Non Existent name";
        when(planetRepository.findByName(name)).thenReturn(Optional.empty());

        // sut - system under test
        // Act
        Optional<Planet> sut = planetService.getByName(name);

        // Assert
        assertThat(sut).isEmpty();

    }

    @Test
    public void listPlanets_ReturnsAllPlanets() {
        // Arrange - Preparar
        Example<Planet> query = QueryBuilder.makeQuery(new Planet(PLANET.getClimate(), PLANET.getTerrain()));

        when(planetRepository.findAll(query)).thenReturn(List.of(PLANET));

        // sut - system under test
        // Act
        List<Planet> sut = planetService.list(PLANET.getTerrain(), PLANET.getClimate());

        // Assert
        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(PLANET);
        // assertThat(sut).isEqualTo(List.of(PLANET));

    }

    @Test
    public void listPlanets_ReturnsNoPlanets() {
        // Arrange - Preparar
        when(planetRepository.findAll(any())).thenReturn(Collections.emptyList());

        // sut - system under test
        // Act
        List<Planet> sut = planetService.list(PLANET.getTerrain(), PLANET.getClimate());

        // Assert
        assertThat(sut).isEmpty();

    }

    @Test
    void removePlanet_WithExistingId_DoesNotThrowAnyException() {
        assertThatCode(() -> planetService.remove(1L)).doesNotThrowAnyException();
    }

    @Test
    void removePlanet_WithNonExistingId_DoesNotThrowAnyException() {
        // Arrange - Preparar
        doThrow(new RuntimeException()).when(planetRepository).deleteById(999L);

        // Executar o teste - Expectativas - Asserções
        assertThatThrownBy(() -> planetService.remove(999L)).isInstanceOf(RuntimeException.class);

    }
}
