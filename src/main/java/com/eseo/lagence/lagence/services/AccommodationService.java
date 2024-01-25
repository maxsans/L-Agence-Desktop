package com.eseo.lagence.lagence.services;

import com.eseo.lagence.lagence.models.Properties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.entity.mime.StringBody;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.tika.Tika;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccommodationService {
    public static ObservableList<Properties> getAccommodations() {

        JsonNode rootNode = RequestService.getInstance().sendHttpRequest("/properties", RequestService.HttpMethod.GET, Optional.empty());

        JsonNode propertiesNode = rootNode.get("properties");

        if (propertiesNode != null && propertiesNode.isArray()) {
            List<Properties> accommodations = new ArrayList<>();

            for (JsonNode accommodationNode : propertiesNode) {
                String id = accommodationNode.get("id").asText();
                String name = accommodationNode.get("name").asText();
                Double price = accommodationNode.get("price").asDouble();
                Integer chargesPrice = accommodationNode.get("chargesPrice").asInt();
                String description = accommodationNode.get("description").asText();
                String address = accommodationNode.get("address").asText();
                Integer roomsCount = accommodationNode.get("roomsCount").asInt();
                Integer surface = accommodationNode.get("surface").asInt();
                String type = accommodationNode.get("type").asText();

                Properties accommodation = new Properties(id, name, price, chargesPrice, description, address, roomsCount, surface, type);
                accommodations.add(accommodation);
            }

            return FXCollections.observableArrayList(accommodations);
        } else {
            System.err.println("Unexpected JSON structure. Unable to deserialize accommodations.");
            return FXCollections.observableArrayList();
        }
    }

    public static boolean createProperty(Properties property, List<File> images) {
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            // Add properties fields
            builder.addPart("name", new StringBody(property.getName(), ContentType.TEXT_PLAIN));
            builder.addPart("description", new StringBody(property.getDescription(), ContentType.TEXT_PLAIN));
            builder.addPart("address", new StringBody(property.getAddress(), ContentType.TEXT_PLAIN));
            builder.addPart("price", new StringBody(String.valueOf(property.getPrice()), ContentType.TEXT_PLAIN));
            builder.addPart("chargesPrice", new StringBody(String.valueOf(property.getChargesPrice()), ContentType.TEXT_PLAIN));
            builder.addPart("surface", new StringBody(String.valueOf(property.getSurface()), ContentType.TEXT_PLAIN));
            builder.addPart("type", new StringBody(property.getType(), ContentType.TEXT_PLAIN));
            builder.addPart("roomsCount", new StringBody(String.valueOf(property.getRoomsCount()), ContentType.TEXT_PLAIN));

            // Add image files
            if (images != null) {
                for (File image : images) {
                    Tika tika = new Tika();
                    String mimeType = tika.detect(image);

                    FileBody fileBody = new FileBody(image, ContentType.create(mimeType), image.getName());
                    builder.addPart("images", fileBody);
                }
            }

            HttpEntity requestEntity = builder.build();

            String endpoint = "/properties";
            HttpPost httpPost = new HttpPost(endpoint);
            httpPost.setEntity(requestEntity);

            JsonNode jsonResponse = RequestService.getInstance().sendHttpRequest(httpPost);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateProperty(Properties accommodation) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Convert Accommodation object to JSON string
            ObjectNode accommodationJsonNode = objectMapper.createObjectNode();
            accommodationJsonNode.put("id", accommodation.getId());
            accommodationJsonNode.put("name", accommodation.getName());
            accommodationJsonNode.put("price", accommodation.getPrice());
            accommodationJsonNode.put("chargesPrice", accommodation.getChargesPrice());
            accommodationJsonNode.put("description", accommodation.getDescription());
            accommodationJsonNode.put("address", accommodation.getAddress());
            accommodationJsonNode.put("roomsCount", accommodation.getRoomsCount());
            accommodationJsonNode.put("surface", accommodation.getSurface());
            accommodationJsonNode.put("type", accommodation.getType());

            // Convert JSON object to JSON string
            String accommodationJson = objectMapper.writeValueAsString(accommodationJsonNode);

            System.out.println(accommodationJson);

            // Send PUT request with JSON body
            JsonNode rootNode = RequestService.getInstance().sendHttpRequest("/properties/" + accommodation.getId(), RequestService.HttpMethod.PUT, Optional.of(accommodationJson));

            // You can handle the response as needed
            System.out.println("Response from updateProperty: " + rootNode);

            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
