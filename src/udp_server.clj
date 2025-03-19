(ns udp-server
  (:gen-class)
  (:import (java.net DatagramPacket DatagramSocket InetAddress)))

(defn start-server [port]
  (let [socket (DatagramSocket. port)
        buffer (byte-array 1024)]
    (println (str "Servidor UDP rodando na porta " port))
    (while true
      (let [packet (DatagramPacket. buffer (count buffer))]
        (.receive socket packet) ;; Aguarda mensagem
        (let [received-data (String. (.getData packet) 0 (.getLength packet))
              sender-address (.getAddress packet)
              sender-port (.getPort packet)]
          (println (str "Recebido: " received-data " de " sender-address ":" sender-port))
          ;; Enviando resposta de volta
          (let [response-data (str "Eco: " received-data)
                response-buffer (.getBytes response-data)
                response-packet (DatagramPacket. response-buffer (count response-buffer) sender-address sender-port)]
            (.send socket response-packet)))))))

(defn -main []
  (start-server 5000))
