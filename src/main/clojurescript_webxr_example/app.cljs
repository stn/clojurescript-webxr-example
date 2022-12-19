(ns clojurescript-webxr-example.app
      (:require ["three" :as three]
                ["three/examples/jsm/webxr/VRButton.js" :refer (VRButton)]))

(defonce scene (three/Scene.))
(defonce camera (three/PerspectiveCamera. 75 (/ (.-innerWidth js/window) (.-innerHeight js/window)) 0.1 1000))
(defonce renderer (doto (three/WebGLRenderer.)
                        (.setSize (.-innerWidth js/window) (.-innerHeight js/window))
                        (->> .-domElement
                             (.appendChild (.-body js/document)))))

(defonce geometry (three/BoxGeometry. 1 1 1))
(defonce material (three/MeshBasicMaterial. #js {:color 0x00ff00}))
(defonce cube (three/Mesh. geometry material))

(defn animate []
      (set! (.. cube -rotation -x) (+ (.. cube -rotation -x) 0.01))
      (set! (.. cube -rotation -y) (+ (.. cube -rotation -y) 0.01))
      (.render renderer scene camera))

(defn init []
      (.add scene cube)
      (set! (.. camera -position -z) 5)
      (.appendChild (.-body js/document) (.createButton VRButton renderer))
      (set! (.. renderer -xr -enabled) true)
      (.setAnimationLoop renderer animate))
