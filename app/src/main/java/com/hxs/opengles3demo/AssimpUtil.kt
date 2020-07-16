package com.hxs.opengles3demo

import assimp.*

object AssimpUtil {
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
            it.meshes[0].vertices[0]
        }
    }


    private fun processNode(node:AiNode, scene:AiScene) {
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
}