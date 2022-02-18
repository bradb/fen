(ns fen.core
  (:require [clojure.string :as str]))

;; todo
;; - add specs

(def ^:private white-kingside-castle \K)
(def ^:private white-queenside-castle \Q)
(def ^:private black-kingside-castle \k)
(def ^:private black-queenside-castle \q)
(def ^:private black-piece (set "rnbqkp"))
(def ^:private white-piece (set "RNBQKP"))
(def ^:private empty-squares-n (set "12345678"))
(def ^:private empty-square \-)
(def ^:private rank-separator #"/")

(defn fen->map
  "Convert a FEN string into a board representation.

  Board representation is a map with the following keys:

  :fen/board - A one-dimensional vector describing the board state,
               from a1 (index 0) to h8 (index 63)
  :fen/allow-white-kingside-castle? - A boolean
  :fen/allow-white-queenside-castle? - A boolean
  :fen/allow-black-kingside-castle? - A boolean
  :fen/allow-black-queenside-castle? - A boolean
  :fen/side-to-move - :white or :black
  :fen/en-passant-target-square - A coll of squares
  :fen/halfmove-clock - An integer
  :fen/fullmove-number - An integer"
  [fen]
  (let [[pieces
         active-colour
         castling-availability
         en-passant-target-square
         halfmove-clock
         fullmove-number]
        (str/split fen #" ")

        castling-opts (into #{} castling-availability)

        allow-castle? (partial contains? castling-opts)

        board (->> (str/split pieces rank-separator)
                   (map (fn expand-rank [rank]
                          (for [piece rank]
                            (cond
                              (white-piece piece) piece

                              (black-piece piece) piece

                              (empty-squares-n piece)
                              (let [n (Character/digit piece 10)]
                                (repeat n empty-square))

                              :else (throw (str "unrecognised piece pattern: " piece))))))
                   flatten)]
    {:fen/board board
     :fen/side-to-move (case active-colour
                         "w" :white
                         "b" :black
                         (throw (str "don't know how to parse active colour " active-colour)))
     :fen/fullmove-number (Integer/parseInt fullmove-number)
     :fen/halfmove-clock (Integer/parseInt halfmove-clock)
     :fen/en-passant-target-square (if (= "-" en-passant-target-square)
                                     nil
                                     en-passant-target-square)
     :fen/allow-white-kingside-castle? (allow-castle? white-kingside-castle)
     :fen/allow-white-queenside-castle? (allow-castle? white-queenside-castle)
     :fen/allow-black-kingside-castle? (allow-castle? black-kingside-castle)
     :fen/allow-black-queenside-castle? (allow-castle? black-queenside-castle)}))

(defn map->fen
  "Convert a board map into a FEN string.

  See `fen->board` for the format of the map."
  [board])
