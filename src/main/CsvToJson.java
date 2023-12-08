package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.result.InsertOneResult;

import model.Cast;
import model.Movie;

public class CsvToJson {
	static List<Movie> movies = new ArrayList<Movie>();
	static int counter = 0;

	public static void readCsv() throws IOException {
		System.out.println("---------- Inside readCsv() ! -------------");
		BufferedReader br = new BufferedReader(new FileReader("IMDB Top 250 Movies.csv"));
		br.readLine();

		while (br.ready()) {
			String line = br.readLine();
			String[] val = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

			int rank = Integer.parseInt(val[0]);
			String name = val[1];
			int year = Integer.parseInt(val[2]);
			double rating = Double.parseDouble(val[3]);
			String[] genres = (val[4].split(","));
			String certificate = val[5];
			String run_time = val[6];
			String tagline = val[7];
			int budget = Integer.parseInt(val[8]);
			long box_office = Long.parseLong(val[9]);
			String[] actors = (val[10].split(","));
			String[] directors = (val[11].split(","));
			String[] writers = (val[12].split(","));

			// Uklanjanje sa pocetka
			actors[0] = actors[0].substring(1, actors[0].length());

			// Uklanjanje sa kraja

			actors[actors.length - 1] = actors[actors.length - 1].substring(0, actors[actors.length - 1].length() - 1);

			// Uklanjanje sa pocetka i kraja writers
			if (writers.length > 1) {
				writers[0] = writers[0].substring(1, writers[0].length());
				writers[writers.length - 1] = writers[writers.length - 1].substring(0,
						writers[writers.length - 1].length() - 1);
			}

			// Uklanjanje sa pocetka i kraja Directors

			if (directors.length > 1) {
				directors[0] = directors[0].substring(1, directors[0].length());
				directors[directors.length - 1] = directors[directors.length - 1].substring(0,
						directors[directors.length - 1].length() - 1);
			}

			// Uklanjanje sa pocetka i kraja Genres
			if (genres.length > 1) {
				genres[0] = genres[0].substring(1, genres[0].length());
				genres[genres.length - 1] = genres[genres.length - 1].substring(0,
						genres[genres.length - 1].length() - 1);
			}

			Cast cast = new Cast(directors, actors, writers);
			Movie m = new Movie(rank, year, rating, budget, box_office, cast, name, run_time, tagline, genres,certificate);
			movies.add(m);
		}
		br.close();
	}

	public static void writeToJson() {
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		try {

			om.writeValue(new File("FullDocMovies" + counter + ".json"), movies.get(counter));
			System.out.println(movies.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void fillDataBase(MongoCollection<Document> coll) throws FileNotFoundException, IOException {
		for (int i = 0; i < 250 ; i++) {
			try {
				// Bulk Approach:
				int count = 0;
				int batch = 100;
				List<InsertOneModel<Document>> docs = new ArrayList<>();

				try (BufferedReader br = new BufferedReader(new FileReader("FullDocMovies" + i + ".json"))) {
					String line;
					while ((line = br.readLine()) != null) {
						docs.add(new InsertOneModel<>(Document.parse(line)));
						count++;
						if (count == batch) {
							coll.bulkWrite(docs, new BulkWriteOptions().ordered(false));
							docs.clear();
							count = 0;
						}
					}
				}

				if (count > 0) {
					BulkWriteResult bulkWriteResult = coll.bulkWrite(docs, new BulkWriteOptions().ordered(false));
					System.out.println("Inserted" + bulkWriteResult);
				}

			} catch (MongoWriteException e) {
				System.out.println("Error");
			}
		}

	}

	public static void main(String[] args) throws IOException {
		readCsv();
		while (counter < 250) {
			writeToJson();
			counter++;
		}

		String connectionString = "mongodb+srv://admin:admin@cluster0.1un9ruy.mongodb.net/test?retryWrites=true&w=majority";
		ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
		MongoClientSettings settings = MongoClientSettings.builder()
				.applyConnectionString(new ConnectionString(connectionString)).serverApi(serverApi).build();
		// Create a new client and connect to the server
		try (MongoClient mongoClient = MongoClients.create(settings)) {
			try {
				// Send a ping to confirm a successful connection
				MongoDatabase database = mongoClient.getDatabase("test");
				MongoCollection<Document> collection = database.getCollection("movies");

				fillDataBase(collection);

				database.runCommand(new Document("ping", 1));
				System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
				InsertOneResult result = collection.insertOne(new Document());
				// Prints the ID of the inserted document
				System.out.println("Success! Inserted document id: " + result.getInsertedId());

				// Prints a message if any exceptions occur during the operation
			} catch (MongoException me) {
				System.err.println("Unable to insert due to an error: " + me);

			}
		}
	}

}
