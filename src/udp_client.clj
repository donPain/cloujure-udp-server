(ns udp-client
  (:import (java.net DatagramPacket DatagramSocket InetAddress)))

(defn send-message [message host port]
  (let [socket (DatagramSocket.)
        address (InetAddress/getByName host)
        buffer (.getBytes message)
        packet (DatagramPacket. buffer (count buffer) address port)]
    (.send socket packet)
    ;; Recebendo resposta
    (let [response-buffer (byte-array 1024)
          response-packet (DatagramPacket. response-buffer (count response-buffer))]
      (.receive socket response-packet)
      (println (str "Resposta do servidor: " (String. (.getData response-packet) 0 (.getLength response-packet)))))
      (.close socket)))

(defn -main [& args]
  (let [msg (if (empty? args) "Olá, servidor!" (first args))]
    (send-message msg "localhost" 5000)))
