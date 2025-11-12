package microservicio.usuario.utils.dataManager;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVRecord;

import microservicio.usuario.entity.Cuenta;
import microservicio.usuario.entity.Usuario;
import microservicio.usuario.repository.CuentaRepository;
import microservicio.usuario.repository.UsuarioRepository;

public class PopulatedUsuariosCuentas extends Populated<Usuario> {
    private final UsuarioRepository usuarioRepository;
    private final CuentaRepository cuentaRepository;

    public PopulatedUsuariosCuentas(Path filePath, UsuarioRepository usuarioRepository, CuentaRepository cuentaRepository) {
        super(filePath);
        this.usuarioRepository = usuarioRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public void poblar() throws IOException, ParseException {
        Iterable<CSVRecord> records = this.read();
        List<Usuario> modificados = new ArrayList<>();

        for (CSVRecord record : records) {
            try {
                String usuarioIdStr = record.get("usuarioId");
                String cuentaIdStr = record.get("cuentaId");
                if (usuarioIdStr == null || usuarioIdStr.isBlank() || cuentaIdStr == null || cuentaIdStr.isBlank()) {
                    System.err.println("[PopulatedUsuariosCuentas.poblar] Registro con columnas vacías: " + record.toString());
                    continue;
                }
                Long usuarioId = Long.parseLong(usuarioIdStr.trim());
                Long cuentaId = Long.parseLong(cuentaIdStr.trim());

                Optional<Usuario> optU = this.usuarioRepository.findById(usuarioId);
                if (optU.isEmpty()) {
                    System.err.println("[PopulatedUsuariosCuentas.poblar] Usuario no encontrado id=" + usuarioId);
                    continue;
                }
                Optional<Cuenta> optC = this.cuentaRepository.findById(cuentaId);
                if (optC.isEmpty()) {
                    System.err.println("[PopulatedUsuariosCuentas.poblar] Cuenta no encontrada id=" + cuentaId);
                    continue;
                }

                Usuario usuario = optU.get();
                Cuenta cuenta = optC.get();

                if (usuario.getCuentas() == null) {
                    usuario.setCuentas(new ArrayList<>());
                }
                if (!usuario.getCuentas().contains(cuenta)) {
                    usuario.getCuentas().add(cuenta);
                    modificados.add(usuario);
                }
            } catch (Exception e) {
                System.err.println("[PopulatedUsuariosCuentas.poblar] ERROR procesando registro: " + record.toString() + " | causa: " + e.getClass().getName() + " - " + e.getMessage());
            }
        }

        if (!modificados.isEmpty()) {
            this.usuarioRepository.saveAll(new ArrayList<>(new LinkedHashSet<>(modificados)));
            System.out.println("[PopulatedUsuariosCuentas.poblar] Asociaciones aplicadas para " + modificados.size() + " relaciones (usuarios afectados deduplicados).");
        } else {
            System.out.println("[PopulatedUsuariosCuentas.poblar] No se aplicaron asociaciones.");
        }
    }
}
