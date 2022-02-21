(ns fen.core
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def ^:private white-kingside-castle \K)
(def ^:private white-queenside-castle \Q)
(def ^:private black-kingside-castle \k)
(def ^:private black-queenside-castle \q)
(def ^:private black-piece (set "rnbqkp"))
(def ^:private white-piece (set "RNBQKP"))
(def ^:private empty-squares-n (set "12345678"))
(def ^:private empty-square \-)
(def ^:private rank-separator #"/")
(def ^:private colour->kw {"w" :white, "b" :black})
(def ^:private kw->colour (set/map-invert colour->kw))

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
  :fen/en-passant-target-square - A string (e.g. \"e3\")
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
     :fen/side-to-move (if-let [c (colour->kw active-colour)]
                         c
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

(defn- empty-square?
  [sq]
  (= sq empty-square))

(defn map->fen
  "Convert a FEN map into a FEN string.

  See `fen->map` for the format of the map."
  [fen-map]
  (let [{:fen/keys [board
                    side-to-move
                    fullmove-number
                    halfmove-clock
                    en-passant-target-square
                    allow-white-kingside-castle?
                    allow-white-queenside-castle?
                    allow-black-kingside-castle?
                    allow-black-queenside-castle?]} fen-map

        board->fen (fn [board]
                     (let [fen-ranks
                           (for [rank (partition 8 board)]
                             (let [parts (partition-by empty-square? rank)]
                               (reduce
                                 (fn [fen-str [x & _ :as frag]]
                                   (if (empty-square? x)
                                     (str fen-str (count frag))
                                     (str fen-str (str/join frag))))
                                 ""
                                 parts)))]
                       (str/join "/" fen-ranks)))

        castling-opts (->> [(when allow-white-kingside-castle?
                              "K")
                            (when allow-white-queenside-castle?
                              "Q")
                            (when allow-black-kingside-castle?
                              "k")
                            (when allow-black-queenside-castle?
                                  "q")]
                           (remove nil?))

        active-colour (kw->colour side-to-move)
        piece-placement (board->fen board)]
    (format "%s %s %s %s %d %d"
            piece-placement
            active-colour
            (if (empty? castling-opts)
              \-
              (str/join castling-opts))
            (or en-passant-target-square empty-square)
            (or halfmove-clock 0)
            (or fullmove-number 0))))
