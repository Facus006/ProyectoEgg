package com.equipoC.Trendytouch.Servicios;

import com.equipoC.Trendytouch.Entidades.Imagen;
import com.equipoC.Trendytouch.Entidades.Publicacion;
import com.equipoC.Trendytouch.Entidades.Usuario;
import com.equipoC.Trendytouch.Enums.Categoria;
import com.equipoC.Trendytouch.Errores.MyException;
import com.equipoC.Trendytouch.Repositorios.PublicacionRepositorio;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PublicacionServicio {

    @Autowired
    private PublicacionRepositorio publicacionRepo;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    ImagenServicio imagenservicio;

    @Transactional
    public void registrarPublicacion(String descripcion, Usuario usuario, String categoria,
            List<MultipartFile> Fotos) throws MyException {

        validar(descripcion, categoria, Fotos);
        
        List<MultipartFile> primeras5Fotos = Fotos.stream().limit(5).collect(Collectors.toList());

        categoria = categoria.toUpperCase();

        Publicacion publi = new Publicacion();
        Date fecha = new Date();
        publi.setFechaPublicacion(fecha);
        publi.setDescripcion(descripcion);
        publi.setUsuario(usuario);
        publi.setCategoria(Categoria.valueOf(categoria));

        List<Imagen> fotos = imagenservicio.guardarLista(primeras5Fotos);
        publi.setFotos(fotos);
        publicacionRepo.save(publi);

    }


    @Transactional
    public void eliminarPublicacionPorId(String idpublicacion) throws MyException {

        Optional<Publicacion> respuesta = publicacionRepo.findById(idpublicacion);
        if (respuesta.isPresent()) {
            publicacionRepo.deleteById(idpublicacion);
        }
    }

    @Transactional(readOnly = true)
    public List<Publicacion> listarPublicaciones() {

        List<Publicacion> publicaciones = publicacionRepo.findAll();

        return publicaciones;
    }

    @Transactional(readOnly = true)
    public List<Publicacion> listarPublicacionesMegustas() {
        List<Publicacion> publicaciones = publicacionRepo.findAll();

        Comparator<Publicacion> comparadorMeGustas = new Comparator<Publicacion>() {
            @Override
            public int compare(Publicacion p1, Publicacion p2) {
                return Integer.compare(p2.getMegusta().size(), p1.getMegusta().size());
            }
        };

        Collections.sort(publicaciones, comparadorMeGustas);

        int numPublicacionesDeseadas = Math.min(10, publicaciones.size()); // Para evitar un índice fuera de rango
        List<Publicacion> primeras10Publicaciones = publicaciones.subList(0, numPublicacionesDeseadas);

        return primeras10Publicaciones;
    }

    public Publicacion getOne(String id) {
        return publicacionRepo.getOne(id);
    }

    @Transactional(readOnly = true)
    public List<Publicacion> buscarUsuario(Usuario usuario) {
        return publicacionRepo.buscarUsuario(usuario);
    }

    private void validar(String descripcion, String categoria, List<MultipartFile> Fotos) throws MyException {

        if (descripcion.isEmpty() || descripcion == null) {
            throw new MyException("La descripcion no puede estar vacia.");
        }
        if (categoria.isEmpty() || categoria == null || categoria=="VACIO") {
            throw new MyException("La categoria no puede estar vacia.");
        }
        if (categoria == "VACIO") {
            throw new MyException("La categoria no puede estar vacia.");
        }
        if (Fotos.isEmpty() || Fotos == null) {
            throw new MyException("Debes subir al menos una foto.");
        }
       
    }
}
