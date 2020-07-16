package com.hxs.opengles3demo

import assimp.*
import com.hxs.opengles3demo.obj.FLOAT_BYTE_COUNT
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

object AssimpHelper {
    private val importer = Importer()


    fun loadModel(path: String) {
        val scene = importer.readFile(
            path,
            AiPostProcessStep.Triangulate.i or AiPostProcessStep.FlipUVs.i
        )
        if (scene?.rootNode == null || scene.flags or AI_SCENE_FLAGS_INCOMPLETE != 0) {
            println("hxs:${importer.errorString}")
            return
        }
        scene.let {
            it.meshes[0]
        }
    }


    private fun processNode(node: AiNode, scene: AiScene) {
        // 处理节点所有的网格（如果有的话）
//        for(index in 0 until node.numMeshes)
//        {
//            val mesh = scene.meshes[index]
//
//            aiMesh *mesh = scene->mMeshes[node->mMeshes[i]];
//            meshes.push_back(processMesh(mesh, scene));
//        }
//        // 接下来对它的子节点重复这一过程
//        for(unsigned int i = 0; i < node->mNumChildren; i++)
//        {
//            processNode(node->mChildren[i], scene);
//        }


    }

    private fun processMesh(mesh: AiMesh, scene: AiScene): Mesh {
        val vMesh = Mesh()
        vMesh.vertices = ByteBuffer.allocateDirect(mesh.numVertices * 8 * FLOAT_BYTE_COUNT)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        vMesh.vertices.position(0)
        // 处理顶点
        for (i in 0 until mesh.numVertices) {

            mesh.vertices[i] to vMesh.vertices
            mesh.normals[i] to vMesh.vertices
            if (!mesh.textureCoords[0].isNullOrEmpty()) {
                mesh.textureCoords[0][i] to vMesh.vertices
            } else {
                AiVector2D(0f, 0f) to vMesh.vertices
            }
        }

        for (face in mesh.faces) {
//            for(index in face)
        }

        return vMesh


    }
}

data class Vertex(
    val position: ArrayList<Float>,
    val normal: ArrayList<Float>,
    val texCoords: ArrayList<Float>
)

data class Texture(val id: Int, val type: Int)

class Mesh {
    lateinit var vertices: FloatBuffer
    val indices: ArrayList<Int> = arrayListOf()
    val textures: ArrayList<Texture> = arrayListOf()

}