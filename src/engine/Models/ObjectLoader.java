package engine.Models;


import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ObjectLoader {
    public static RawModel loadObject(String pathToObject, Loader3Dmodel loader) {
        FileReader fr = null;
        try {
            fr = new FileReader(new File((pathToObject + ".obj")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(fr);
        String line;
        List<Vector3f> vertices = new ArrayList<Vector3f>();
        List<Vector2f> textures = new ArrayList<Vector2f>();
        List<Vector3f> normals = new ArrayList<Vector3f>();
        List<Integer> indices = new ArrayList<Integer>();

        float[] vertecesArray = null;
        float[] normalsArray = null;
        float[] texturesArray = null;
        int[] indicesArray = null;
        int stevec = 0;

        float minY = 0.0f;
        float maxY = 0.0f;
        float minX = 0.0f;
        float maxX = 0.0f;
        float minZ = 0.0f;
        float maxZ = 0.0f;

        try {
            while (true) {

                line = reader.readLine();
                String[] currnetLine = line.split(" ");
                if (line.startsWith("v ")) {
                    Vector3f vertex = new Vector3f(Float.parseFloat(currnetLine[2]),
                            Float.parseFloat(currnetLine[3]), Float.parseFloat(currnetLine[4]));
                    vertices.add(vertex);
                    if (vertices.size() > 1) {
                        if (Float.parseFloat(currnetLine[2]) > maxX) maxX = Float.parseFloat(currnetLine[2]);
                        if (Float.parseFloat(currnetLine[3]) > maxY) maxY = Float.parseFloat(currnetLine[3]);
                        if (Float.parseFloat(currnetLine[4]) > maxZ) maxZ = Float.parseFloat(currnetLine[4]);
                        if (Float.parseFloat(currnetLine[2]) < minX) minX = Float.parseFloat(currnetLine[2]);
                        if (Float.parseFloat(currnetLine[3]) < minY) minY = Float.parseFloat(currnetLine[3]);
                        if (Float.parseFloat(currnetLine[4]) < minZ) minZ = Float.parseFloat(currnetLine[4]);
                    } else {
                        minY = Float.parseFloat(currnetLine[3]);
                        maxY = Float.parseFloat(currnetLine[3]);
                        minX = Float.parseFloat(currnetLine[2]);
                        maxX = Float.parseFloat(currnetLine[2]);
                        minZ = Float.parseFloat(currnetLine[4]);
                        maxZ = Float.parseFloat(currnetLine[4]);
                    }
                } else if (line.startsWith("vt ")) {
                    Vector2f texture = new Vector2f(Float.parseFloat(currnetLine[1]),
                            Float.parseFloat(currnetLine[2]));
                    textures.add(texture);
                } else if (line.startsWith("vn ")) {
                    Vector3f normal = new Vector3f(Float.parseFloat(currnetLine[1]),
                            Float.parseFloat(currnetLine[2]), Float.parseFloat(currnetLine[3]));
                    normals.add(normal);
                } else if (line.startsWith("f ")) {
                    //z dva pomnozimo ker ma usaka struktura dva flouta normalo pa z 3 ker ima 3 floute
                    texturesArray = new float[vertices.size() * 2];
                    normalsArray = new float[vertices.size() * 3];
                    break;
                }
                stevec++;
            }
            //loopamo skuzi sve face vrstice
            while (line != null) {
                stevec++;
                if (!line.startsWith("f ")) {
                    line = reader.readLine();
                    continue;
                }
                String[] currentLine = line.split(" ");

                if (currentLine.length == 4) {
                    String[] vertex1 = currentLine[1].split("/");
                    String[] vertex2 = currentLine[2].split("/");
                    String[] vertex3 = currentLine[3].split("/");
                    processVertex(vertex1, indices, textures, normals, texturesArray, normalsArray);
                    processVertex(vertex2, indices, textures, normals, texturesArray, normalsArray);
                    processVertex(vertex3, indices, textures, normals, texturesArray, normalsArray);
                } else if (currentLine.length == 5) {
                    String[] vertex1 = currentLine[1].split("/");
                    String[] vertex2 = currentLine[2].split("/");
                    String[] vertex3 = currentLine[3].split("/");
                    processVertex(vertex1, indices, textures, normals, texturesArray, normalsArray);
                    processVertex(vertex2, indices, textures, normals, texturesArray, normalsArray);
                    processVertex(vertex3, indices, textures, normals, texturesArray, normalsArray);

                    String[] vertex11 = currentLine[1].split("/");
                    String[] vertex23 = currentLine[3].split("/");
                    String[] vertex4 = currentLine[4].split("/");
                    processVertex(vertex11, indices, textures, normals, texturesArray, normalsArray);
                    processVertex(vertex23, indices, textures, normals, texturesArray, normalsArray);
                    processVertex(vertex4, indices, textures, normals, texturesArray, normalsArray);
                }

                //postavimo normalo in texture na normalno pozicijo
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Object: " + pathToObject);
        System.out.println(maxX + " - " + minX + " - " + (Math.abs(maxX) + Math.abs(minX)));
        System.out.println(maxY + " - " + minY + " - " + (Math.abs(maxY) + Math.abs(minY)));
        System.out.println(maxZ + " - " + minZ + " - " + (Math.abs(maxZ) + Math.abs(minZ)));
        System.out.println("");
        float[] model_size = {maxX, minX, maxY, minY, maxZ, minZ};

        vertecesArray = new float[vertices.size() * 3];
        indicesArray = new int[indices.size()];

        int vertexPointer = 0;
        for (Vector3f vertex : vertices) {
            vertecesArray[vertexPointer++] = vertex.getX();
            vertecesArray[vertexPointer++] = vertex.getY();
            vertecesArray[vertexPointer++] = vertex.getZ();
        }

        for (int i = 0; i < indices.size(); i++) {
            indicesArray[i] = indices.get(i);
        }
        return loader.loadToVAO(vertecesArray, texturesArray, indicesArray, normalsArray, model_size);
    }

    private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals, float[] textureArray, float[] normalsArray) {
        if (Integer.parseInt(vertexData[1]) - 1 < textures.size()) {
            int currentVertexpointer = Integer.parseInt(vertexData[0]) - 1;
            indices.add(currentVertexpointer);
            Vector2f curentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
            textureArray[currentVertexpointer * 2] = curentTex.getX();
            textureArray[currentVertexpointer * 2 + 1] = 1 - curentTex.getY();
            Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
            normalsArray[currentVertexpointer * 3] = currentNorm.getX();
            normalsArray[currentVertexpointer * 3 + 1] = currentNorm.getY();
            normalsArray[currentVertexpointer * 3 + 2] = currentNorm.getZ();
        }


    }
}
