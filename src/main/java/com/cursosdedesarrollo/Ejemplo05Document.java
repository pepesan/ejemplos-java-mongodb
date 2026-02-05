package com.cursosdedesarrollo;

import org.bson.Document;
import org.bson.BsonTimestamp;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public class Ejemplo05Document {

    public static void main(String[] args) {

        // _id generado manualmente (ObjectId)
        ObjectId id = new ObjectId();
        // Crear un Document simple
        Document doc = new Document("_id", id)
                .append("nombre", "Ana")
                .append("edad", 30)
                .append("activo", true);

        // Document con estructura anidada
        Document direccion = new Document("ciudad", "Madrid")
                .append("cp", "28001");

        doc.append("direccion", direccion);

        // Document con array
        doc.append("roles", List.of("ADMIN", "USER"));

        // Fecha (BSON Date)
        doc.append("fechaAlta", new Date());

        // Timestamp BSON (segundos desde epoch + incremento)
        BsonTimestamp timestamp = new BsonTimestamp(
                (int) (System.currentTimeMillis() / 1000),
                1
        );
        doc.append("ultimoCambio", timestamp);

        // Mostrar el Document
        System.out.println(doc.toJson());
    }
}
