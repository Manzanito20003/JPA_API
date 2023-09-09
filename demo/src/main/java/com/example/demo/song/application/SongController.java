package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; //sirve para que al ejecutar un programa nos de un codigo
//de respuesta al procesar una solicitud HTTP (ejm. 200 = OK, 404 = Not Found, 500 = Internal Server Error)
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map; //Lo uso para el patch
import java.util.List;

@RestController
@RequestMapping("/song")

public class SongController {

    @Autowired
    private SongRepository songRepository; //lo que hace es interactuar con la base de datos
    //en este caso con List<Song> interactua directamente con la base de dato de la clase Song
    //Esta base de datos se crea al meterle elementos en postman (elementos Song),
    //y los guarda ahí en songRepository para despues interactuar con su base de datos.
    //Osea songRepository es donde se crea la base de datos y se interactua para
    //poder crear los metodos.

    @GetMapping// si no ponemos nada, el link tendrá que ponerse con el RequesMapping
    //en este caso https: local_host/song

    //ResponseEntity esta relacionado a lo que se enviara como solicitud web
    //El ResponseEntity<> nos almacena los como si fuera una lista pero con condicion del contenido
    //que se quiere almacenar, en si enviando todo el contenido como una solicitud web.
    //para despues trabajar con ello. (encapsula o almacena todo el contenido que se enviara como
    // solicitud a la web).
    @GetMapping
    public ResponseEntity<List<Song>> songs() { //Esta funcion sirve para retornar todas los atributos
        //privados de la función Song
        List<Song> songs = songRepository.findAll(); //esto sirve para interactuar y permitir acceder a
        // los atributos privados de la lista de Song
        //Para eso List<Song> songs, es la lista de clases
        //con el findAll() accedo al contenido de esa lista, para interactuar con ella
        //Es como si estuviera seleccionando todo el song, para cada elemento de la lsita.
        return new ResponseEntity<>(songs, HttpStatus.OK); //El ResponseEntity<>, recorre
        //cada elemento de la lista de song y envia una solicitus a la web, si funciona, dira OK
        //lo cual significa que la solicitud fue exitosa y ahí recien empieza la ejecución
    }

    @GetMapping("/{id}") //para que lea el id exacto
    //@PathVariable sirve para acceder
    //a uno de los atributos exactos de la clase
    public ResponseEntity<Song> song(@PathVariable ("id") Long id) {
        Song song = songRepository.findById(id).orElse(null);
        //findById() nos busca el parametro de la clase en la base de datos y si lo encuentra
        //guarda el contenido en song.
        //orElse(null) nos dice que si no se encuentra el parametro en la base de datos, pues
        //ocasiona que song no apunte a nada(null)

        if(song == null){
            //si la clase con ese id no existe pues que retorne que no NOT FOUND
            return new ResponseEntity<>(song, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(song, HttpStatus.OK);
        //si la cancion si se encuentra, pues que retorne OK
    }

    @PostMapping
    public ResponseEntity<String> song(@RequestBody Song song) { //El @RequestBody Song song,
        //este apartado nos esta indicando que creamos un objeto de la clase Song y el
        //RequestBody al crearla, nos permite poner los atributos cuando utilize el metodo post
        //Se coloca todo dentro de una song() para que todo ese contenido se cree en song e iguale
        //con cada atributo

        //RespondeEntity<String> nos sirve para que almacene el contenido de lo que hay dentro del <>
        //osea que aca solo estamos guardando los String nada mas,

        songRepository.save(song); // Guarda todo el contenido en la base de datos.

        return ResponseEntity.status(201).body("Created"); //nos dice que si cumple
        //la condicion de 201 que nos de como respuesta (el .body) pues "Created"
        //como medio de decir que la solicitud fue exitosa y que el contenido esta en la base de datos
        //el RespondeEntity.status, nos dice que que si la solcitiud web es del estado 201 peus que cumpla
    }


    @PutMapping("/{id}")
    public ResponseEntity<Song> song(@PathVariable ("id") Long id,@RequestBody Song pe){
        //El @RequestBody es para crear un nuevo objeto desde la web
        //El @PathVariable es para buscar un elemento del objeto
        Song song = songRepository.findById(id).orElse(null);
        //si la id no se encuentra pues se convierte en null

        if(song == null){
            //si la clase con ese id no existe pues que retorne que no NOT FOUND
            return new ResponseEntity<>(song, HttpStatus.NOT_FOUND);
        }

        song.setId(id); //pongo solo la id, porque es la di que queremos actaulizar
        //si intento actualizar tambien la id, aparece error
        song.setTitle(pe.getTitle());
        song.setArtist(pe.getArtist());
        song.setAlbum(pe.getAlbum());
        song.setReleaseDate(pe.getReleaseDate());
        song.setGenre(pe.getGenre()); //el pe es el objeto con todos las variables puestas
        //los cuales al crear otro objeto song, pues estamos guardando a song los elementos de pe
        song.setDuration(pe.getDuration());
        song.setCoverImage(pe.getCoverImage());
        song.setSpotifyUrl(pe.getSpotifyUrl());

        songRepository.save(song); //aca actualizo justaamente la posicion de la id
        //con todos los datos de pe.

        return new ResponseEntity<>(song,HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Song> song(@PathVariable ("id") Long id, @RequestBody Map<String,String> pe){
        //Falta colocar el mapacon todos los tipos de datos de las variables de la clase Song.

        //en @PatchVariable ("id") Long id, esta extrayendo la id de la base de datos y lo guarda
        //en Long id

        Song song = songRepository.findById(id).orElse(null);

        if(song == null){
            return new ResponseEntity<>(song, HttpStatus.NOT_FOUND);
        }

        if(pe.containsKey("title")){ //verifica si en postman se escribio "title" : nuevotitulo;

            String newTtitle = pe.get("title");
            //Si en el postman escribrimos "title" : hola, esta llamando el resultado
            //de "title" y lo guarda en newTitle

            song.setTitle(newTtitle);//Eso mismo lo guardamos en song.
        }

        if(pe.containsKey("artist")){
            String newArtist = pe.get("artist");
            song.setArtist(newArtist);
        }


        songRepository.save(song); //Esto actualiza solo los valores que estan en Song,
        //los demas que estan en vacio, no los actauliza.

        return new ResponseEntity<>(song,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCancion(@PathVariable("id") Long id) {
        Song song = songRepository.findById(id).orElse(null);

        if (song == null) {
            return new ResponseEntity<>("El recurso no se encontró", HttpStatus.NOT_FOUND);
        }

        songRepository.delete(song);
        return new ResponseEntity<>("Se elimino con exito!",HttpStatus.OK);
    }

    // TODO: Añadir métodos  DELETE
}
